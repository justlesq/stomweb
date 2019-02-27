package com.sohu.stom.modules.business.task;

import com.sohu.stom.common.utils.DateUtils;
import com.sohu.stom.common.utils.IOSUtil;
import com.sohu.stom.common.utils.StringUtils;
import com.sohu.stom.modules.business.entity.KeyWordSort;
import com.sohu.stom.modules.business.entity.TaskInfo;
import com.sohu.stom.modules.business.service.KeyWordSortService;
import com.sohu.stom.modules.business.service.TaskInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yxf on 17/10/24.
 */
@Service
@Lazy(false)
@EnableScheduling
public class KeyWordSortTask {

    public static List<TaskInfo> TASK_INFO_LIST = new ArrayList<TaskInfo>();


    @Autowired
    private TaskInfoService taskInfoService;

    @Autowired
    private KeyWordSortService keyWordSortService;


    /**
     *
     *
     */
    //@Scheduled(cron = "0 */30 * * * ?")
    public void searchKeyWord(){



        List<TaskInfo> tempTaskInfoList  = taskInfoService.findTaskOnShelves();

        if(CollectionUtils.isEmpty(tempTaskInfoList)){

        }else{
            if(CollectionUtils.isEmpty(TASK_INFO_LIST)){
                TASK_INFO_LIST = tempTaskInfoList;
            }else{
                if(tempTaskInfoList.size() != TASK_INFO_LIST.size()){
                    TASK_INFO_LIST.clear();
                    TASK_INFO_LIST = tempTaskInfoList;
                }
            }

            List<KeyWordSort> keyWordSortList = keyWordSortService.findKeyWordSort(new KeyWordSort());


            if(CollectionUtils.isNotEmpty(keyWordSortList)){
                for (TaskInfo taskInfo : TASK_INFO_LIST) {
                    KeyWordSort key = new KeyWordSort();
                    key.setAppid(taskInfo.getAppleid());
                    key.setKeyword(taskInfo.getKeyword());
                    KeyWordSort keyWordSort = keyWordSortService.getKeyWordSort(key);
                    if(keyWordSort!=null){
                        System.out.println("更新----------------------");
                        int newRank = IOSUtil.getAppRank(keyWordSort.getKeyword(),keyWordSort.getAppid());
                        String createTime =  DateUtils.formatTimeInMillis(System.currentTimeMillis());
                        int hour = Integer.parseInt(createTime.substring(11,13));
                        //把所有日期的24点排名放进数组
                        String allSortTime[] = keyWordSort.getKeysort().split(";");
                        //取数组最后一个元素与当前日期比较
                        String lastDate = allSortTime[allSortTime.length - 1].split(":")[0];
                        //每次过来取最后一个日期的排名
                        String sort[] = allSortTime[allSortTime.length - 1].split(":")[1].split(",");
                        //若不等于当前时间则累加（重新初始化当天的24小时排名进行替换） 否则则更新
                        if(createTime.substring(0,10).equals(lastDate)){
                            System.out.println("更新当天数据----------------------");
                            //排除异常
                            //if(newRank != 0){
                                String wordRank = replaceSort(hour,Integer.toString(newRank),sort);
                                System.out.println("当天新排名:"+wordRank);
                                String keyWordRank =  createTime.substring(0,10) + ":" + wordRank;
                                //删除数组最后一个元素
                                String newAllSortTime[] = ArrayUtils.remove(allSortTime,allSortTime.length-1);
                                //添加更新排名后的新元素
                                String resultNewSort[] = ArrayUtils.add(newAllSortTime,keyWordRank);
                                //遍历拼接成字符串形式存入数据库
                                StringBuffer b = new StringBuffer();
                                for (int i = 0; i <resultNewSort.length ; i++) {
                                    b.append(resultNewSort[i] + ";");
                                }
                                keyWordSort.setKeysort(b.toString());
                            //}
                        }else{
                            System.out.println("拼接旧数据,构造第二天数据-------------");

                            /**
                             * 这里取今天0点的新排名放入昨天的24点
                             */
                            String oldAllSortTime[] = keyWordSort.getKeysort().split(";");
                            //取昨天的数据
                            String oldSort[] = oldAllSortTime[oldAllSortTime.length - 1].split(":")[1].split(",");
                            StringBuffer newB = new StringBuffer();
                            if(oldSort.length == 25){
                                String wordRank = replaceSort(24,Integer.toString(newRank),oldSort);

                                String newKeyWordRank = DateUtils.getYesterday() + ":" + wordRank;
                                //删除数组最后一个元素
                                String newAllSortTime[] = ArrayUtils.remove(oldAllSortTime,oldAllSortTime.length-1);
                                //添加更新排名后的新元素
                                String resultNewSort[] = ArrayUtils.add(newAllSortTime,newKeyWordRank);


                                for (int i = 0; i <resultNewSort.length ; i++) {
                                    newB.append(resultNewSort[i] + ";");
                                }

                            }


                            System.out.println(String.format("更新昨日%s24点的数据------------%s",DateUtils.getYesterday(),newB.toString()));
                            System.out.println(String.format("昨日24点数据更新完,开始拼接今天%s数据-----------------------",createTime.substring(0,10)));

                            StringBuffer b = new StringBuffer();
                            for(int  i = 0 ;i< 25; i++){
                                b.append(0 + ",");
                            }
                            //初始化24个小时的排名都为0
                            String newSort[] = b.deleteCharAt(b.length() - 1).toString().split(",");
                            //排除异常
                            //if(newRank!=0){
                                String todayWordRank = replaceSort(hour,Integer.toString(newRank),newSort);
                                if(StringUtils.isNotEmpty(newB.toString())){
                                    String keyWordRank = newB.toString() + createTime.substring(0,10) + ":" + todayWordRank + ";";
                                    keyWordSort.setKeysort(keyWordRank);
                                }else{
                                    String keyWordRank = keyWordSort.getKeysort() + createTime.substring(0,10) + ":" + todayWordRank + ";";
                                    keyWordSort.setKeysort(keyWordRank);
                                }


                            //}
                        }
                        keyWordSortService.update(keyWordSort);
                    }else{
                        System.out.println("初始化新增----------------------");
                        StringBuffer b = new StringBuffer();
                        for(int  i = 0 ;i< 25; i++){
                            b.append(0 + ",");
                        }
                        //初始化24个小时的排名都为0
                        String sort[] = b.deleteCharAt(b.length() - 1).toString().split(",");


                        KeyWordSort newKeyWordSort = new KeyWordSort();
                        newKeyWordSort.setKeyword(taskInfo.getKeyword());
                        newKeyWordSort.setAppname(taskInfo.getAppname());
                        newKeyWordSort.setAppid(taskInfo.getAppleid());
                        String createTime =  DateUtils.formatTimeInMillis(System.currentTimeMillis());
                        int hour = Integer.parseInt(createTime.substring(11,13));
                        int newKeyWordRank = IOSUtil.getAppRank(taskInfo.getKeyword(), taskInfo.getAppleid());
                        //排除异常
                        //if(newKeyWordRank!=0){
                            String wordRank = replaceSort(hour,Integer.toString(newKeyWordRank),sort);
                            String keyWordRank =  createTime.substring(0,10) + ":" + wordRank + ";";
                            newKeyWordSort.setKeysort(keyWordRank);
                            keyWordSortService.saveKeyWordSort(newKeyWordSort);
                        //}
                    }
                }
                
            }else{
                System.out.println("开始初始化----------------------");
                StringBuffer b = new StringBuffer();
                for(int  i = 0 ;i< 25; i++){
                    b.append(0 + ",");
                }
                //初始化24个小时的排名都为0
                String sort[] = b.deleteCharAt(b.length() - 1).toString().split(",");

                //取出已上架的关键词
                for (TaskInfo taskInfo: TASK_INFO_LIST) {
                    KeyWordSort keyWordSort = new KeyWordSort();
                    String appId = taskInfo.getAppleid();
                    String appName = taskInfo.getAppname();
                    String keyWord = taskInfo.getKeyword();
                    String createTime =  DateUtils.formatTimeInMillis(System.currentTimeMillis());
                    System.out.println(createTime);
                    int hour = Integer.parseInt(createTime.substring(11,13));
                    int newKeyWordRank = IOSUtil.getAppRank(keyWord, appId);
                    //排除异常
                    //if(newKeyWordRank !=0){
                        String wordRank = replaceSort(hour,Integer.toString(newKeyWordRank),sort);
                        String keyWordRank =  createTime.substring(0,10) + ":" + wordRank + ";";
                        System.out.println(newKeyWordRank);
                        keyWordSort.setAppid(appId);
                        keyWordSort.setAppname(appName);
                        keyWordSort.setKeyword(keyWord);
                        keyWordSort.setKeysort(keyWordRank);
                        keyWordSortService.saveKeyWordSort(keyWordSort);
                    //}
                }
            }
        }
    }




    public static String replaceSort(int hour,String newSort,String[] sort){
        System.out.println(String.format("替换前--------------当前为%d点,新的排名是%s,当前24小时所有排名为%s",hour,newSort,Arrays.toString(sort)));
        if(hour == 24){
            Arrays.fill(sort,24,25,newSort);
        }else{
            Arrays.fill(sort,hour,hour+1,newSort);
        }

        System.out.println(String.format("替换后--------------当前为%d点,新的排名是%s,当前24小时所有排名为%s",hour,newSort,Arrays.toString(sort)));
        StringBuffer b = new StringBuffer();
        for (int i = 0; i <sort.length ; i++) {
            b.append(sort[i] + ",");
        }
        return b.deleteCharAt(b.length() - 1).toString();
    }
}
