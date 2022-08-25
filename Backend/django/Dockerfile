# Python 3.8 Docker Image 사용
FROM python:3.8

# 컨테이너 내 코드 실행 경로 설정
RUN mkdir -p /webserver/src
WORKDIR /webserver/src

ENV PYTHONUNBUFFERED=0
ENV PYTHONIOENCODING=utf-8

# Chrome, xvfb 설치 & Chromedriver 다운로드
# RUN sed -i 's@archive.ubuntu.com@mirror.kakao.com@g' /etc/apt/sources.list
RUN apt-get update
RUN wget -qP /tmp/ "https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"
RUN apt install /tmp/google-chrome-stable_current_amd64.deb -y
RUN version=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE") && wget -qP /tmp/ "https://chromedriver.storage.googleapis.com/${version}/chromedriver_linux64.zip"
RUN unzip -o /tmp/chromedriver_linux64.zip -d /webserver
RUN chmod 755 /webserver/chromedriver
RUN apt-get install xvfb -y

# requirements.txt의 Python Packages 설치
COPY requirements.txt ./
RUN pip install --upgrade pip
RUN pip install -r requirements.txt
