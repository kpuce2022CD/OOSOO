<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->



<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->




<!-- PROJECT LOGO -->
![header](https://capsule-render.vercel.app/api?type=soft&color=gradient&customColorList=0,2,30&text=OOSOO%20:%20Capstone%20Design&fontSize=40&animation=twinkling&section=header)
<br />
<div align="center">
  <a href="https://github.com/kpuce2022CD/OOSOO">
    <img src = "https://user-images.githubusercontent.com/78994323/148485426-b4673791-d032-412c-acc5-5b24c5dadf2e.png" width="30%" height="30%">
  </a>

<h3 align="center">OTT 서비스 연동 어플리케이션</h3>
<h4 align="center"><i>OTT service interworking application using web crawling</i></h4>
  <p align="center">
    여러 OTT 서비스를 하나의 어플리케이션에서 연동시켜 이용할 수 있도록 하는 서비스입니다.
    <br />
    <a href="https://ovenapp.io/view/9Le85BqDAqYOPNOC3RZbMeC2QqoU0BSO/"><strong>프로토타입 보러가기 »</strong></a>
    <br />
    <a href="https://www.miricanvas.com/v/1ss123">Project Proposal</a> · <a href="https://github.com/kpuce2022CD/OOSOO/wiki">Wiki</a> · <a href="https://trello.com/b/LZtvi1s0">Trello</a>
  </p>
</div>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#background">Background</a></li>
        <li><a href="#goals">Goals</a></li>
        <li><a href="#built-with">Bulit With</a></li>
      </ul>
    </li>
    <li>
      <a href="#description">Description</a>
      <ul>
        <li><a href="#system-scenario">System Scenario</a></li>
        <li><a href="#system-config">System Config</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage(buliding..)</a></li>
    <li><a href="#collaborator">Collaborator</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project


<!-- 프로젝트 main description field-->

### Background
 * 최근 넷플릭스, 왓챠 등 영상 스트리밍 플랫폼이 늘어나며 **여러 플랫폼의 구독권을 동시에 구매하는 사용자가 증가**
 * 각각의 [OTT서비스](https://ko.wikipedia.org/wiki/OTT_%EC%84%9C%EB%B9%84%EC%8A%A4)는 각자 분리되어 있어 여러 플랫폼을 이용하고 있는 사용자는 **각각의 플랫폼마다 별도로
관리해줘야 하는 불편함**


 
### Goals

* 웹 크롤링을 통해 각각의 OTT 서비스를 **하나의 통합된 앱으로 관리**할 수 있도록 하는 OTT 통합 연동 플랫폼을 개발
  - 통합검색 서비스를 만들고 **해당 컨텐츠가 어떤 플랫폼에 존재**하는지 알려주며 원할 때 해당 플랫폼의 앱으로 리다이렉션
  - 여러 플랫폼에서 **시청중인 컨텐츠와 찜 목록을 하나의 앱에서 확인**하고 각 OTT에 동기화  
  
* 다양한 컨텐츠를 즐기는 기존 사용자들과 희망하던 잠재적 사용자들에게 편리함을 제공
* 각 OTT플랫폼의 사용자들에 동시 구독의 장벽을 낮출 수 있으며, 이로 인한 각 OTT 플랫폼에 추가적인 경제적 효과도 기대할 수 있음

### Built With


* ![AndroidStudio](https://img.shields.io/badge/-AndroidStudio-000000?style=flat&logo=android)
* ![AWS EC2](https://img.shields.io/badge/-EC2-000000?style=flat&logo=amazon-aws)
* ![Django](https://img.shields.io/badge/-Django-000000?style=flat&logo=django)
* ![Nginx](https://img.shields.io/badge/-Nginx-000000?style=flat&logo=nginx)
* ![Gunicorn](https://img.shields.io/badge/-Gunicorn-000000?style=flat&logo=gunicorn)
* ![MariaDB](https://img.shields.io/badge/-MariaDB-000000?style=flat&logo=mariadb)
* ![Selenium](https://img.shields.io/badge/-Selenium-000000?style=flat&logo=selenium)





<!-- DESCRIPTION -->
## Description

<summary><strong>주요 기능</strong></summary>
<ol>
  <li>통합 검색 서비스</li>
  <li><strong>컨텐츠 추천 알고리즘</strong></li>
  <li><strong>구독중인 OTT 컨텐츠 제공</strong></li>
  <li><strong>시청 중인 컨텐츠나 찜한 목록을 조회 및 동기화</strong></li>
  <li>사용자 연령에 맞는 추천 컨텐츠 제공</li>
  <li>게시판을 통한 구독 서비스를 추천 및 가격 비교</li>
  <li>추가적인 UI 디자인 제공</li>
</ol>


### System Scenario
<img src = "https://user-images.githubusercontent.com/78994323/148479674-9cddf23b-8cea-46f6-a8ea-7daaf6cca931.png" width="50%" height="50%">
<br />

### System Config
<img src = "https://user-images.githubusercontent.com/78994323/148479749-f1a5b87e-5be5-447d-9948-c09c897d0873.png" width="70%" height="70%">
<br />

## Usage
<img src = "https://user-images.githubusercontent.com/78994323/148479535-a21236bc-a69d-42dc-8697-1096c15cf50c.jpg" width="30%" height="30%">
Plz look forward to it..😅




<!-- GETTING STARTED
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.
* npm
  ```sh
  npm install npm@latest -g
  ```

### Installation

1. Get a free API Key at [https://example.com](https://example.com)
2. Clone the repo
   ```sh
   git clone https://github.com/github_username/repo_name.git
   ```
3. Install NPM packages
   ```sh
   npm install
   ```
4. Enter your API in `config.js`
   ```js
   const API_KEY = 'ENTER YOUR API';
   ```





<!-- USAGE EXAMPLES 
## Usage

Use this space to show useful examples of how a project can be used. Additional screenshots, code examples and demos work well in this space. You may also link to more resources.

_For more examples, please refer to the [Documentation](https://example.com)_




<!-- ROADMAP 
## Roadmap

- [] Feature 1
- [] Feature 2
- [] Feature 3
    - [] Nested Feature

See the [open issues](https://github.com/github_username/repo_name/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>   -->



<!-- CONTRIBUTING 
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p> -->



<!-- LICENSE 
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p> -->




<!-- ACKNOWLEDGMENTS
## Acknowledgments

* []()
* []()
* []()

<p align="right">(<a href="#top">back to top</a>)</p>  -->



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

## Collaborator

 * [박찬호](https://github.com/hoho-97) : cksgh5652@gmail.com
 * [김재현](https://github.com/eoeo0326) : eoeo0326@gmail.com
 * [김진호](https://github.com/Jihn0118) : lantern50@kpu.ac.kr

<p align="right">(<a href="#top">back to top</a>)</p>
