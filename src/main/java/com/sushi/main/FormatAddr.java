package com.sushi.main;

import com.sushi.method.RuleMethods;
import com.sushi.pojo.Rule;
import com.sushi.pojo.RuleNode;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Author sushi
 * @create 2019-11-15 3:34 PM
 */
public class FormatAddr {
    public static void main(String[] args) {
        System.out.println(RuleMethods.matchAddress("13箱沧林159号104室沧一小区19#楼"));
        System.out.println(RuleMethods.matchAddress("5箱沧湖东一里259店旭日5期"));
        System.out.println(RuleMethods.matchAddress("13箱沧林159号104室沧一小区19#楼"));
        System.out.println(RuleMethods.matchAddress("13# 海发 112#305沧二"));
        System.out.println(RuleMethods.matchAddress("14# 双桥一里7# 502室"));
        System.out.println(RuleMethods.matchAddress("13#108室S12嵩屿北一里浪琴湾二期125箱"));
    }
}
