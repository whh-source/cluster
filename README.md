# cluster(集群方式)

#### 1, redis 集群连接方式
 
    如果想定主读从，要重写 RedisTemplate,
    可参考： https://github.com/whh-redis/redis-sentinel

#### 2, redis 能力发掘

##### a) key - value 结构
    redis 基本最常用的的是 key -value结构， 过于复杂的数据，可先转json 再存。
    json的方式就是不灵活, 应该使用 key 
        