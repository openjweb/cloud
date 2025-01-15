package org.openjweb.cms.lucene;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ChineseSearch {



    public static List<String> search(String indexDir, String q) throws Exception {

        //获取要查询的路径，也就是索引所在的位置
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        //使用中文分词器
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        //由中文分词器初始化查询解析器
        QueryParser parser = new QueryParser("desc", analyzer);
        //通过解析要查询的 String，获取查询对象
        Query query = parser.parse(q);

        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        //开始查询，查询前 10 条数据，将记录保存在 docs 中
        TopDocs docs = searcher.search(query, 10);
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        log.info("匹配{}共耗时{}毫秒", q, (endTime - startTime));
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
            log.info("city:{}", doc.get("city"));
            log.info("desc:{}", doc.get("desc"));
            String desc = doc.get("desc");

            //显示高亮
            if(desc != null) {
                TokenStream tokenStream = analyzer.tokenStream("desc", new StringReader(desc));
                String summary = highlighter.getBestFragment(tokenStream, desc);
                log.info("高亮后的desc:{}", summary);
                list.add(summary);
            }
        }
        reader.close();
        return list;
    }

    public static void main(String[] args){
        String indexDir = "D:\\lucene2";
        //String q = "南京文化";
        String q = "security";
        List<String> list = null;

        try {
            list = ChineseSearch.search(indexDir, q);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if(list!=null&&list.size()>0){
            for (String str:list){
                log.info(str);
            }
        }
    }
}
