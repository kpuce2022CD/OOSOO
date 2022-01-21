# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from pyvirtualdisplay import Display
import time


def w_watchings(email, pwd, name):
    result = list()

    display = Display(visible=0, size=(1920, 1080))     # PyCharm 테스트시 주석처리
    display.start()                                     # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (AWS 경로 : "/home/ubuntu/django_server/chromedriver")
    driver = webdriver.Chrome("/home/ubuntu/django_server/chromedriver")  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"

    # 실행할 웹페이지 불러오기(왓챠 로그인 화면)
    driver.get("https://watcha.com/sign_in")

    # 로그인
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[1]/input').send_keys(email)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[2]/input').send_keys(pwd)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[3]/button').click()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1blyq76")))

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('css-1blyq76')

    for profile in profiles:
        print(profile.text)
        if profile.text == name:
            profile.click()

    time.sleep(3)
    driver.implicitly_wait(5)

    # ----------------------------------------------------------------------------------------------------------------------#

    # '이어보기' 에 있는 항목들 불러오기
    driver.get("https://watcha.com/watchings")

    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1g3awd")))

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

    watchings = driver.find_elements_by_class_name('css-1g3awd')
    print("< Watching List >")
    for watch in watchings:
        print(watch.text)
        result.append(watch.text)

    return result
