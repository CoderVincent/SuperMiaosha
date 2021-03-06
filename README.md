# SuperMiaosha
一个不断更新迭代的秒杀系统

> 本系统是一个高并发的秒杀系统，原始版本来自于若鱼老师的教学视频https://coding.imooc.com/class/168.html 秒杀系统是一个互联网场景下很典型的高并发系统，最初的学习目的也是想通过做一个秒杀系统更多的了解如何应对高并发场景。在学习完老师的思想后，我会持续给系统补充更多细节，打造一个更加完善的秒杀系统，也希望能将其中的思想迁移到其他的系统当中。

## 秒杀系统优化策略

面对高并发大流量冲击，秒杀系统能否支撑的住还需要做一系列优化。总的思路是让响应结果在越前面的位置返回越好，减少请求。然后从前端向底层进行优化。

### 页面缓存

前端请求传到Controller，通常会经过渲染然后将页面返回到前端。如果页面中的内容不经常更新就可以将页面静态化，将渲染后的页面保存起来并放入缓存，下次请求的时候直接从缓存中取出页面然后返回即可。

### 对象缓存

在前端和数据库之间加一层redis缓存，内存的数据请求速度是非常快的，如果将数据放到缓存下次请求就可以直接从内存中取这条数据而不需要再访问数据库。但是要注意数据库和缓存的数据一致性，例如更新数据的时候一定要先更新数据库再更新缓存，否则会造成数据库和缓存的数据不一致。

### 页面静态化

秒杀页面访问量大，如果每个人进来都需要动态的从后台去请求数据那么势必带来大量的并发冲垮页面，而秒杀的商品一般不会有信息上的改动，所以我们通过将页面静态化可以极大的提高我们的速度。

### 静态资源优化（合并js和css请求）

通过Nginx配置将静态资源合并传输

### redis预减库存

用redis去记录库存情况，这样在请求过来时就可以判断商品是否还有剩余，如果没有剩余就直接返回秒杀失败，有剩余就进行接下来的操作，并且把缓存中的库存数目减1.

### 使用本地标识截断请求

这个本地标识可以在负载均衡层做也可以在内存做，前者就是在负载均衡维持一个布尔变量，标记秒杀是否结束，如果结束直接返回秒杀结束的响应结果就好，而不用继续接下来的流程。如果放在内存做，就是在代码中维持一个布尔类型的全局变量即可，用来标记秒杀是否结束。

### 利用消息队列异步下单


