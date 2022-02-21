# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from pyvirtualdisplay import Display
from api.Netflix.Login import n_login
import time


def n_wishes(email, pwd, name):
    #display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    #display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"    # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    #options.add_argument("--proxy-server=socks5://127.0.0.1:9150")     # Tor 설치 후 시도
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)

    # 넷플릭스 로그인
    n_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    # 찜 목록 불러오기
    result = list()

    driver.get("https://www.netflix.com/browse/my-list")

    time.sleep(3)
    driver.implicitly_wait(5)

    wishes = driver.find_elements(By.CLASS_NAME, 'fallback-text')

    # 찜목록 출력
    print("< Wishlist >")
    for wish in wishes:
        print(wish.text)
        result.append(wish.text)

    driver.quit()
    #display.stop()

    return result
