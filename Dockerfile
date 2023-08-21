# base image
FROM amazoncorretto:17

# 변수설정 (빌드파일의 경로)
ARG BOOT_JAR=build/libs/*.jar
# 빌드파일을 컨테이너로 복사
COPY ${BOOT_JAR} boot.jar
# jar 파일 실행
ENTRYPOINT ["java", "-jar", "/boot.jar"]