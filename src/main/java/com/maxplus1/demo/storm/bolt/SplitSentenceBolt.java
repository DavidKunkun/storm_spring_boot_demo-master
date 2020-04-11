package com.maxplus1.demo.storm.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * Created by xiaolong.qiu on 2017/3/28.
 */
public class SplitSentenceBolt extends BaseBasicBolt {

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        String sentence = input.getStringByField("value");
        for(int i=0;i<sentence.length();i++){
            char word = sentence.charAt(i);
            collector.emit(new Values(word+""));//分词emit，只有1个单词的元组Tuple
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word")); //分词定义的field为word
    }
}
