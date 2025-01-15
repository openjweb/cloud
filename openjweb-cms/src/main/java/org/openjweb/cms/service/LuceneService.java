package org.openjweb.cms.service;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.openjweb.cms.entity.CmsInfo;
import org.openjweb.common.util.CMSUtil;
import org.openjweb.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Component
@Data
public class LuceneService {
    /**
     * 存放索引的位置
     */
    private Directory dir;

    @Value("${lucene.cmsDir:}")
    String indexPath;//

    @Resource
    private CmsInfoService cmsInfoService;


    private IndexWriter getWriter() throws Exception {
        //使用中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //将中文分词器配到写索引的配置中
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //实例化写索引对象
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    public void initIndex(String indexDir) throws Exception {
        dir = FSDirectory.open(Paths.get(indexDir));
        IndexWriter writer = getWriter();
        //close 了才真正写到文档中
        writer.close();
    }

    public void initIndex() throws Exception {
        log.info("配置的indexPath:");
        log.info(this.indexPath);
        dir = FSDirectory.open(Paths.get(this.indexPath));
        IndexWriter writer = getWriter();
        //close 了才真正写到文档中
        writer.close();
    }

    public void addIndex(List<CmsInfo> cmsInfoList) throws Exception {

        //将CMS表的
        log.info("luceneService开始添加cmsInfo的分词..索引文件路径.........."+this.indexPath);

        dir = FSDirectory.open(Paths.get(this.indexPath));//不设置的话会提示空指针
        IndexWriter writer = getWriter();
        Document doc = new Document();

        for (CmsInfo infEnt : cmsInfoList) {
            log.info("为新闻"+infEnt.getRowId()+"添加分词........");
            String infTitle = infEnt.getInfTitle() == null ? "" : infEnt.getInfTitle();//标题
            String infContent = infEnt.getInfContent() == null ? "" : infEnt.getInfContent();//正文
            //infContent过滤掉html标签
            //infContent = "本公司招聘兼职销售代表"+StringUtil.getUUID();//
            infContent = StringUtil.getHtmlText(infContent);

            if(infContent.indexOf("兼职")>-1){
                log.info("有兼职，正文:"+infContent);
            }
            else{
                infContent = "张三李四王五";
                continue; //
            }


            String infSummary = infEnt.getInfSummary() == null ? "" : infEnt.getInfSummary();//摘要
            String infAuthor = infEnt.getInfAuthor() == null ? "" : infEnt.getInfAuthor();//作者
            String publishDt = infEnt.getPublishDt() == null ? "" : infEnt.getPublishDt();//发布日期
            String url = infEnt.getInfUrl() == null ? "" : infEnt.getInfUrl();//新闻的URL链接
            String cateTreeCode = infEnt.getCateTreeCode() == null ? "" : infEnt.getCateTreeCode();//所属栏目编码
            String rowId = infEnt.getRowId();
            //区分不同的类

            /*doc.add(new Field("clsName", "CmsInfo", Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("id", rowId, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("title", infTitle, Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("contents", infContent, Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("summary", infSummary, Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field("pubDate", publishDt.substring(0, 16), Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("author", infAuthor, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("path", url, Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("category", cateTreeCode, Field.Store.YES, Field.Index.NOT_ANALYZED));
            */
            doc.add(new StringField("clsName", "CmsInfo", Field.Store.YES ));
            doc.add(new StringField("id", rowId, Field.Store.YES ));
            doc.add(new TextField("title", infTitle, Field.Store.YES ));
            doc.add(new TextField("contents", infContent, Field.Store.YES ));
            doc.add(new TextField("summary", infSummary, Field.Store.YES ));
            doc.add(new StringField("pubDate", publishDt.substring(0, 16), Field.Store.YES ));
            doc.add(new StringField("author", infAuthor, Field.Store.YES ));
            doc.add(new StringField("path", url, Field.Store.YES ));
            doc.add(new StringField("category", cateTreeCode, Field.Store.YES ));

            writer.addDocument(doc);
            log.info("为新闻"+infEnt.getRowId()+"添加完毕........");

        }
        log.info("分词写入文件开始。。。。。。");

        writer.close();
        log.info("分词写入文件完成。。。。。。");


    }

    public   List<String> search(String indexDir, String searchContent) throws Exception {

        log.info("正在搜索的关键词::::");
        log.info(searchContent);

        //获取要查询的路径，也就是索引所在的位置
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        //使用中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //由中文分词器初始化查询解析器
        QueryParser parser = new QueryParser("contents", analyzer);
        //通过解析要查询的 String，获取查询对象
        Query query = parser.parse(searchContent);

        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        //开始查询，查询前 10 条数据，将记录保存在 docs 中
        TopDocs docs = searcher.search(query, 10);
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        log.info("匹配{}共耗时{}毫秒", searchContent, (endTime - startTime));
        log.info("查询到{}条记录", docs.totalHits);

        //如果不指定参数的话，默认是加粗，即 <b><b/>
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red>","</font></b>");
        //根据查询对象计算得分，会初始化一个查询结果最高的得分
        QueryScorer scorer = new QueryScorer(query);
        //根据这个得分计算出一个片段
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        //将这个片段中的关键字用上面初始化好的高亮格式高亮
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        //设置一下要显示的片段
        highlighter.setTextFragmenter(fragmenter);

        //取出每条查询结果
        List<String> list = new ArrayList<>();
        for(ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc 相当于 docID，根据这个 docID 来获取文档
            Document doc = searcher.doc(scoreDoc.doc);

            String desc = doc.get("contents");
            String rowId = doc.get("id");

            //显示高亮
            if(desc != null) {
                TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(desc));
                list.add(desc);
                String summary = highlighter.getBestFragment(tokenStream, desc);
                log.info("高亮后的contents:{}", summary+"--"+rowId);
                list.add(summary+"--"+rowId);
            }
        }
        reader.close();
        return list;
    }



    public static void main(String[] args) throws Exception {

        new LuceneService().initIndex("d:/lucene/cms");

    }


}
