# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Watcha.Login import w_login
from api.Watcha.Login_2 import wat_login
import time
import asyncio


def w_wishes(email, pwd, name):
    #display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    #display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"    # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)

    # 왓챠 로그인
    wat_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    # '보고싶어요' 에 있는 항목들 불러오기
    result = list()

    driver.get("https://watcha.com/wishes")

    time.sleep(3)
    driver.implicitly_wait(5)

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

    time.sleep(3)
    driver.implicitly_wait(5)

    wishes = driver.find_elements_by_class_name('css-1ponucs')

    time.sleep(3)
    driver.implicitly_wait(5)

    print("< Wishlist >")
    for wish in wishes:
        if wish.text != "":
            print(wish.text)
            result.append(wish.text)

    driver.quit()
    #display.stop()

    return result
