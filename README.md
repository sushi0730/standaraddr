# standaraddr
地址分词匹配
输入：
"25箱沧湖东一里259店旭日5期",
"25# 106嵩屿北一里7#楼浪琴湾",
"13箱沧林159号104室沧一小区19#楼",
"13# 海发 112#305沧二",
"14# 双桥一里 7# 502室",
"13#108室S12嵩屿北一里浪琴湾二期125箱"

词性： ns地名 f方向 mq数量 m数词  b区别词
ns_key自己定义的类型，表示地址前缀
（其中 ’一期‘分词后属于区别词，也可以把这种：一～十期 1～10期 自定义特定词性，方便匹配）

1.分词 （用的是ansj）
	（1）配置default.dic
		浪琴湾	ns	1000
		嵩屿	ns	1000
		双桥	ns	1000
		#	q	1000
		沧林路	ns	1000
		沧林	ns_key	1000
		沧湖东一里	ns	1000
		沧一小区	ns	1000
		海发	ns_key	1000
		海发路	ns	1000
	这里需要配置地名，否则分词准确性太低太低，同时对于例如沧林路这种地名可以配置2条记录，提取关键词沧林，
	并标注特定词性，用以匹配地址中不准确的输入.如 沧林路/ns和沧林/ns_key,当匹配到ns_key可以转化成ns

	(2)ambiguity.dic(强制分割)暂时没用到
		配置用法
		沧一小 ns 区 ns      --将会把沧一小区强行分解成 仓一小/ns 和 区/ns

分词过后输出：
25箱/mq,沧湖东一里/ns,259/m,店/n,旭日/n,5期/mq
25/m,#/q, ,106/m,嵩屿/ns,北/f,一里/mq,7/m,#/q,楼/n,浪琴湾/ns
13箱/mq,沧林/ns_key,159号/mq,104室/mq,沧一小区/ns,19/m,#/q,楼/n
13/m,#/q, ,海发/ns_key, ,112/m,#/q,305/m,沧,二/m
14/m,#/q, ,双桥/ns,一里/mq, ,7/m,#/q, ,502室/mq
13/m,#/q,108室/mq,s/en,12/m,嵩屿/ns,北/f,一里/mq,浪琴湾/ns,二期/b,125箱/mq


2.根据词性和大量输入设置规则（可以配置多条）
ns|ns_key - [f] - [b] - (mq | m - [q])+

	
3.识别规则，并匹配地址提取地址信息，并优化：(1)ns_key替换成对应ns （2）#替换成合适的量词 （3）遇到m【q】的q缺失时，补充合适的量词
（样例过少，所以可以简单实处理，先‘号’，存在则‘室’，应该根据多一些样例制定）
输出：
沧湖东一里 259(量词--号)     						--匹配
嵩屿 北 一里 7 #(量词--号)	  						--勉强匹配，少了106/m　
沧林(路) 159号 104室  / 沧一小区 19 #（量词--号）	--匹配	
海发(路) 112 #（量词--号） 305 （量词--室）			--匹配
双桥 一里 7 #（量词--号） 502室					--匹配
嵩屿 北 一里 / 浪琴湾 二期 125箱 					--不匹配

分词是关键，现有地名库将是关键，还需要大量测试输入来人工制定规则和优化。






