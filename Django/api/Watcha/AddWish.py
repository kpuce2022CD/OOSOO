# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from pyvirtualdisplay import Display
import time


def w_addwish(email, pwd, name, title):
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

    # 해당 영화 검색

    search = driver.find_element_by_xpath('//*[@id="top_navigation"]/nav/div[1]/div/div/form/label/input')
    search.send_keys(title)
    time.sleep(2)
    search.send_keys(Keys.RETURN)

    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1hqk0rn")))

    results = driver.find_elements_by_class_name('css-1hqk0rn')

    for result in results:
        print(result.text)
        if result.text == title:
            webdriver.ActionChains(driver).move_to_element(result).perform()
            time.sleep(2)
            driver.find_element_by_class_name('css-16g6nz2').click()
            time.sleep(2)
            return "success"
        else:
            return "fail"
