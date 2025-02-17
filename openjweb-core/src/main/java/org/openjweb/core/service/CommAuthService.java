package org.openjweb.core.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.core.entity.CommAuth;
import org.openjweb.core.mapper.CommAuthMapper;
import org.openjweb.core.module.params.CommAuthParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Service
@Slf4j
public class CommAuthService extends ServiceImpl<CommAuthMapper, CommAuth> {
    @Autowired
    private CommAuthMapper commAuthMapper;

    @Resource(name = "jdbcTemplateOne")
    private JdbcTemplate service;


    /**
     * 根据ROWID查询
     *
     * @param rowId
     * @return
     */
    public CommAuth queryByRowId(String rowId) {
        return this.commAuthMapper.queryByRowId(rowId);
    }

    public List<CommAuth> queryList(CommAuthParam param) {
        List list = this.commAuthMapper.queryList(param);
        return list;
    }


    /**
     * 分页查询
     *
     * @param param
     * @return
     */

    public IPage<CommAuth> findPage(CommAuthParam param) {
        Page<CommAuth> page = new Page<>(param.getPage(), param.getPageSize());

        IPage<CommAuth> list = this.commAuthMapper.findPage(page, param);
        return list;
    }

    /**
     * 批量删除
     *
     * @param selectedIds
     * @throws Exception
     */
    public void del(String selectedIds) throws Exception {
        String[] ids = null;
        int delCount = 0;
        if (selectedIds != null && selectedIds.trim().length() > 0) {
            ids = selectedIds.split(",");
            //System.out.println(ids.length);
            List<String> parmList = Arrays.asList(ids);
            delCount = this.commAuthMapper.deleteBatchIds(parmList);
            //this.commApiKeyMapper.
        }
        if (delCount == 0) {
            throw new Exception("删除失败!");
        }
    }

    public JSONArray getVueMenu(String userId) {
        log.info("调用SpringBoot的getVueMenu.................");
        List<CommAuth> list = null;
        JSONArray array0 = new JSONArray();
        try {
            String sql = "";
            QueryWrapper<CommAuth> wrapper = new QueryWrapper();
            //List sortList = new ArrayList();
            //sortList.add("sort_no");
            //sortList.add("comm_code");
            //第一级
            wrapper.eq("data_flg", "Y").eq("is_vue", "Y").like("comm_code", "CRM_MENU__")
                    .apply("LENGTH(comm_code) <= {0}", 10) //限制comm_code为10位
                    .orderBy(true,true,"sort_no,comm_code");
        //.orderByAsc(sortList);
            try {
                list = this.list(wrapper);
                //service.findByHqlQuery("from CommAuth where commCode like 'CRM_MENU__' and dataFlg='Y' and isVue='Y' order by sortNo,commCode ");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            Iterator ir = list.iterator();
            while (ir.hasNext()) {
                CommAuth bean = (CommAuth) ir.next();
                CommAuth authEnt = bean;
                String parentCode = "";
                String funName = bean.getAuthName();
                log.info("菜单名：：：："+funName);
                String commCode = bean.getCommCode();
                int tmpCount = service.queryForObject("select count(*) from v_user_auth where comm_code like ? and login_id=?", new Object[]{commCode + "%", userId}, Integer.class);
                if (tmpCount == 0) continue;
                boolean isLeaf = false;
                //if(bean.getAuthResource()!=null&&bean.getAuthResource().trim().length()>1)
                if (bean.getMenuUrl() != null && bean.getMenuUrl().trim().length() > 1) {
                    isLeaf = true;
                }
                //第一层
                JSONObject json = new JSONObject();
                if (!"Y".equals(authEnt.getIsVue())) continue;
                json.put("path", authEnt.getVuePath());//路径
                json.put("component", authEnt.getVueComponent());//
                if ("Layout".equals(authEnt.getVueComponent())) {
                    json.put("alwaysShow", true);
                }
                if (authEnt.getVueRedirect() != null && authEnt.getVueRedirect().trim().length() > 0) {
                    json.put("redirect", authEnt.getVueRedirect());
                }
                //这个vue和layui的可以一致。
                if (bean.getLayuiName() != null && bean.getLayuiName().trim().length() > 0) {
                    json.put("name", bean.getLayuiName());

                } else {
                    json.put("name", bean.getCommCode());//暂时使用编码
                }
                if ("Y".equals(authEnt.getVueHidden())) {
                    json.put("hidden", true);
                }
                //meta
                JSONObject metaJson = new JSONObject();
                metaJson.put("title", bean.getAuthName());
                if (authEnt.getVueIcon() != null && authEnt.getVueIcon().trim().length() > 0) {
                    metaJson.put("icon", authEnt.getVueIcon());
                }
                json.put("meta", metaJson);


                //第二层循环
                //String sql2 ="select distinct login_id,comm_code,auth_name,auth_resource,pic_file,sort_no,auth_id,menu_url,menu_sort_no ,layui_name,layui_jump from  v_user_auth  where login_id=? and comm_code like ?    order by menu_sort_no,comm_code";

                wrapper = new QueryWrapper();

                wrapper.eq("data_flg", "Y").eq("is_vue", "Y").like("comm_code", authEnt.getCommCode() + "__")
                        .apply("LENGTH(comm_code) <= {0}", 12) //限制comm_code为12位---二级菜单为12位CRM_MENU0101
                        .orderBy(true,true,"menu_sort_no,comm_code");
                        //.orderBy(true,true,"sort_no,comm_code");
                //.orderByAsc(sortList);

                List<CommAuth> list2 = null;
                try {
                    list2 = this.list(wrapper);
                    //service.findByHqlQuery("from CommAuth where commCode like 'CRM_MENU__' and dataFlg='Y' and isVue='Y' order by sortNo,commCode ");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JSONArray array2 = new JSONArray();
                if (list2 != null && list2.size() > 0) {
                    //第二层
                    for (int i = 0; i < list2.size(); i++) {

                        //UserAuthBean bean2 = (UserAuthBean)list2.get(i);
                        CommAuth bean2 = (CommAuth) list2.get(i);

                        JSONObject json2 = new JSONObject();

                        CommAuth authEnt2 = bean2;
                        int tmpCount2 = service.queryForObject("select count(*) from v_user_auth where comm_code like ? and login_id=?", new Object[]{authEnt2.getCommCode() + "%", userId}, Integer.class);
                        if (tmpCount2 == 0) continue;
                        if (!"Y".equals(authEnt2.getIsVue())) continue;

                        json2.put("path", authEnt2.getVuePath());//路径

                        json2.put("component", authEnt2.getVueComponent());//

                        if ("Layout".equals(authEnt2.getVueComponent())) {
                            json2.put("alwaysShow", true);
                        }
                        if ("Layout".equals(authEnt2.getVueComponent())) {
                            json2.put("component", "EmptyLayout");//注意非第一层菜单不能用layout。

                        }
                        if (authEnt2.getVueRedirect() != null && authEnt2.getVueRedirect().trim().length() > 0) {

                            json2.put("redirect", authEnt2.getVueRedirect());

                        }

                        //这个vue和layui的可以一致。
                        if (bean2.getLayuiName() != null && bean2.getLayuiName().trim().length() > 0) {
                            json2.put("name", bean2.getLayuiName());

                        } else {
                            json2.put("name", bean2.getCommCode());//暂时使用编码
                        }
                        if ("Y".equals(authEnt2.getVueHidden())) {
                            json2.put("hidden", true);
                        }
                        //meta

                        JSONObject metaJson2 = new JSONObject();
                        metaJson2.put("title", bean2.getAuthName());
                        if (authEnt2.getVueIcon() != null && authEnt2.getVueIcon().trim().length() > 0) {
                            metaJson2.put("icon", authEnt2.getVueIcon());
                        }

                        json2.put("meta", metaJson2);


                        List<CommAuth> list3 = null;
                        wrapper = new QueryWrapper();


                        wrapper.eq("data_flg", "Y").eq("is_vue", "Y").like("comm_code", authEnt2.getCommCode() + "__")
                                .apply("LENGTH(comm_code) <= {0}", 14) //限制comm_code为12位---二级菜单为12位CRM_MENU0101
                                .orderBy(true,true,"menu_sort_no,comm_code");
                        //.orderByAsc(sortList);


                        try {
                            list3 = this.list(wrapper);
                            //service.findByHqlQuery("from CommAuth where commCode like 'CRM_MENU__' and dataFlg='Y' and isVue='Y' order by sortNo,commCode ");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        JSONArray array3 = new JSONArray();

                        if (list3 != null && list3.size() > 0) {
                            //第二层

                            for (int j = 0; j < list3.size(); j++) {
                                //logger.info("hihihihihi");
                                //UserAuthBean bean3 = (UserAuthBean)list3.get(j);
                                CommAuth bean3 = (CommAuth) list3.get(j);

                                JSONObject json3 = new JSONObject();


                                CommAuth authEnt3 = bean3;
                                int tmpCount3 = service.queryForObject("select count(*) from v_user_auth where comm_code like ? and login_id=?", new Object[]{authEnt3.getCommCode() + "%", userId}, Integer.class);
                                if (tmpCount3 == 0) continue;
                                json3.put("path", authEnt3.getVuePath());//路径
                                json3.put("component", authEnt3.getVueComponent());//
                                if ("Layout".equals(authEnt3.getVueComponent())) {
                                    json3.put("alwaysShow", true);
                                }
                                //a
                                if (authEnt3.getVueRedirect() != null && authEnt3.getVueRedirect().trim().length() > 0) {
                                    json3.put("redirect", authEnt3.getVueRedirect());
                                }
                                //这个vue和layui的可以一致。
                                if (bean3.getLayuiName() != null && bean3.getLayuiName().trim().length() > 0) {
                                    json3.put("name", bean3.getLayuiName());

                                } else {
                                    json3.put("name", bean3.getCommCode());//暂时使用编码
                                }
                                if ("Y".equals(authEnt3.getVueHidden())) {
                                    json3.put("hidden", true);
                                }
                                //meta
                                JSONObject metaJson3 = new JSONObject();
                                metaJson3.put("title", bean3.getAuthName());
                                if (authEnt3.getVueIcon() != null && authEnt3.getVueIcon().trim().length() > 0) {
                                    metaJson3.put("icon", authEnt3.getVueIcon());
                                }
                                json3.put("meta", metaJson3);

                                ////////////////////////开始第四层
                                List<CommAuth> list4 = null;
                                wrapper = new QueryWrapper();
                                wrapper.eq("data_flg", "Y").eq("is_vue", "Y").like("comm_code", authEnt3.getCommCode() + "__")
                                        .apply("LENGTH(comm_code) <= {0}", 16) //限制comm_code为12位---二级菜单为12位CRM_MENU0101
                                        .orderBy(true,true,"menu_sort_no,comm_code");
                                //.orderByAsc(sortList);
                                try {
                                    list4 = this.list(wrapper);
                                    //service.findByHqlQuery("from CommAuth where commCode like 'CRM_MENU__' and dataFlg='Y' and isVue='Y' order by sortNo,commCode ");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                                JSONArray array4 = new JSONArray();
                                if (list4 != null && list4.size() > 0) {
                                    //第二层
                                    for (int k = 0; k < list4.size(); k++) {
                                        CommAuth bean4 = (CommAuth) list4.get(k);
                                        JSONObject json4 = new JSONObject();
                                        CommAuth authEnt4 = bean4;
                                        //if(!"Y".equals(authEnt3.getIsVue()))continue;
                                        int tmpCount4 = service.queryForObject("select count(*) from v_user_auth where comm_code like ? and login_id=?", new Object[]{authEnt4.getCommCode() + "%", userId}, Integer.class);
                                        if (tmpCount4 == 0) continue;
                                        json4.put("path", authEnt4.getVuePath());//路径
                                        json4.put("component", authEnt4.getVueComponent());//
                                        if ("Layout".equals(authEnt4.getVueComponent())) {
                                            json4.put("alwaysShow", true);
                                        }
                                        if (authEnt4.getVueRedirect() != null && authEnt4.getVueRedirect().trim().length() > 0) {
                                            json4.put("redirect", authEnt4.getVueRedirect());
                                        }
                                        //这个vue和layui的可以一致。
                                        if (bean4.getLayuiName() != null && bean4.getLayuiName().trim().length() > 0) {
                                            json4.put("name", bean4.getLayuiName());

                                        } else {
                                            json4.put("name", bean4.getCommCode());//暂时使用编码
                                        }
                                        if ("Y".equals(authEnt4.getVueHidden())) {
                                            json4.put("hidden", true);
                                        }
                                        //meta
                                        JSONObject metaJson4 = new JSONObject();
                                        metaJson4.put("title", bean4.getAuthName());
                                        if (authEnt4.getVueIcon() != null && authEnt4.getVueIcon().trim().length() > 0) {
                                            metaJson4.put("icon", authEnt4.getVueIcon());
                                        }
                                        json4.put("meta", metaJson4);
                                        array4.add(json4);//

                                    }
                                    if (array4 != null && array4.size() > 0) {

                                        json3.put("children", array4);//
                                    }
                                    //array4.add(json4);//向数组中增加菜单项
                                }

                                ///////////////////////结束第四层

                                array3.add(json3);//向数组中增加菜单项
                            }
                            if (array3 != null && array3.size() > 0) {

                                json2.put("children", array3);//向2添加子菜单项
                            }


                        }

                        array2.add(json2);
                    }
                    if (array2 != null && array2.size() > 0) {

                        json.put("children", array2);
                    }
                }

                array0.add(json);
            }
        }
        catch (Exception ex) {
        }
        return array0;
    }
}
