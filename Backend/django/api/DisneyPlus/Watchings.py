# selenium 불러오기
from bs4 import BeautifulSoup
from selenium import webdriver
from pyvirtualdisplay import Display
import time
from api.DisneyPlus.Login import d_login

def d_watchings(email, pwd, name):
    # display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    # display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    #디플 로그인
    d_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    result = list()

    # 페이지 스크롤
    last_height = driver.execute_script("return document.body.scrollHeight")

    while True:
        scroll_down = 0
        while scroll_down < 10:
            driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
            time.sleep(0.2)
            scroll_down += 1

        # 스크롤 내린 후 스크롤 높이 다시 가져옴
        new_height = driver.execute_script("return document.body.scrollHeight")
        if new_height == last_height:
            break
        last_height = new_height

    # bs4 source 토스
    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')

    try:
        watchings = soup.select('#home-collection > div:nth-child(6) > div > div > div > div > div > div')

        # cross-classify type : 분류
        for watching in watchings:
            movie = watching.find("div", attrs={"data-program-type": "movie"})
            episode = watching.find("div", attrs={"data-program-type": "episode"})

            if movie:
                result.append(movie.select('#asset-metadata > h5')[0].text)
            elif episode:
                result.append(episode.select('#asset-metadata > p')[0].text)
    except:
        print("데이터 로드에 오류가 발생했습니다.")
    finally:
        print(result)

    driver.quit()
    # display.stop()

    return result