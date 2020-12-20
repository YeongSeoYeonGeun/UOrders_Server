# UOrders_Server

## Project
* 2020년 2학기 기업사회맞춤형프로젝트 영서연근팀
* 유학생을 위한 교내 카페 사이렌 오더 시스템 Uorders

## 참여 인원
|이름|역할|
|------|---|
|박종근|유학생용 미니프로그램, 점주용 IOS 애플리케이션|
|이영서|점주용 IOS 애플리케이션|


## Dependencies

```
dependencies {
	compile group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1.1'
	compile 'com.fasterxml.jackson.datatype:jackson-datatype-jsr310'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'com.google.firebase:firebase-admin:6.8.1'
	implementation group: 'com.squareup.okhttp3', name: 'okhttp', version: '4.2.2'
	compile 'com.google.cloud:google-cloud-translate:1.95.4'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'mysql:mysql-connector-java'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation('org.springframework.boot:spring-boot-starter-test') {
		exclude group: 'org.junit.vintage', module: 'junit-vintage-engine'
	}
}
```

## 서버 아키텍쳐
![그림1](https://user-images.githubusercontent.com/53558710/102711258-875a5200-42fb-11eb-8ed4-00298f4b3691.png)


## 사용된 도구
* Springboot
* MySQL
* Firebase Cloud Messaging
* Google Cloud Translate
* Okhttp3
* AWS EC2
* AWS RDS
* AWS S3
