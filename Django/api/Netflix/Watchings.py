# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from pyvirtualdisplay import Display
from api.Netflix.Login import n_login
import time


def n_watchings(email, pwd, name):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (AWS 경로 : "/home/ubuntu/django_server/chromedriver")
    driver = webdriver.Chrome("/home/ubuntu/django_server/chromedriver")  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"

    # 왓챠 로그인
    n_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    # 시청중인 목록 불러오기
    result = list()

    driver.get("https://www.netflix.com/browse/continue-watching")

    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "fallback-text")))

    watchings = driver.find_elements(By.CLASS_NAME, 'fallback-text')

    print("< Watching List >")
    for watch in watchings:
        print(watch.text)
        result.append(watch.text)

    return result
