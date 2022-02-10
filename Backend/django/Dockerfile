# Python 3.8 Docker Image 사용
FROM python:3.8

# 컨테이너 내 코드 실행 경로 설정
RUN mkdir -p /webserver/src
WORKDIR /webserver/src

# requirements.txt의 Python Packages 설치
COPY requirements.txt ./
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
