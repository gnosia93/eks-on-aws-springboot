# eks-on-aws-springboot


### Intelij ###

* [실행시 --warning-mode all 오류](https://vanillacreamdonut.tistory.com/252)


### Distributed Tracing ###

* https://stackoverflow.com/questions/71154186/adding-tracer-beans-in-spring-boot-with-opentelemetry


### Intelij 실행환경 설정 ###
그림 처럼 LOKI_URL 환경변수를 설정하고 실행해야 한다. 그렇지 않으면 logback 오류가 발생하면서 실행되지 않는다. 
* logback-springboot.xml 설정에 property 로 선언되어 있다.
* springProperty 태그가 제대로 동작하지 않아서 properties 와 연결하지 못했기 때문에 환경변수로 부터 불러온다. 
```
LOKI_URL=http://localhost:3100/loki/api/v1/push
```
![](https://github.com/gnosia93/eks-on-aws/blob/main/images/intelij-logback-env-1.png)
![](https://github.com/gnosia93/eks-on-aws/blob/main/images/intelij-logback-env-2.png)
