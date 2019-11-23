package com.sushi.method;

import com.sushi.pojo.RuleChain;
import com.sushi.pojo.RuleMatch;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;

import java.util.*;

/**
 * @Author sushi
 * @create 2019-11-16 11:31 PM
 */
public class RuleMethods {
    private static List<RuleChain> ruleChains = new ArrayList<>();
    private static List<MatchManager> managers = new ArrayList<>();

    //匹配器列表 暂时只有写死的一条，即只有一个匹配器
    //ns|ns_key - [f] - [b] - (mq | m - [q])+
    static {

        managers.add(new MatchManager(new RuleChain("")));
    }

    /**
     * 针对地址匹配所有规则
     * @param address
     */
    public static String matchAddress(String address){
        //根据不同规则进行
        Result words = DicAnalysis.parse(address.replace(" ",""));
        //最高分的匹配结果
        MatchManager highManager = null;
        for (MatchManager manager : managers) {
            manager.clear();
            for (int i = 0; i < words.size(); i++) {
                //添加初始的实例
                manager.addOneRuleMatch();
                for(int j = i; j<words.size();j++){
                    Term word = words.get(j);

                    //所有规则匹配单个词
                    manager.allMatchWord(word);
                }
                //结束一种规则的匹配，进行一些结束操作
                manager.finishAllRuleMatch();
            }
            //选出最高得分的结果
            if(highManager == null){
                highManager = manager;
            }else if(highManager.getHighScore() < manager.getHighScore()){
                highManager = manager;
            }

        }
        return highManager.getHighScoreString();

    }


    /**
     * 针对分词结果，匹配某条规则
     * @param words
     * @param manager
     */
    private static void matchWords(Result words, MatchManager manager) {
        Iterator<Term> iterator = words.iterator();
        while (iterator.hasNext()) {
            Term word = iterator.next();
            manager.allMatchWord(word);
        }


    }


}
