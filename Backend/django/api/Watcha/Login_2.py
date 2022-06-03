# selenium 불러오기
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time


def wat_login(email, pwd, name, driver):
    # 실행할 웹페이지 불러오기(왓챠 로그인 화면)
    driver.get("https://watcha.com/sign_in")

    # 로그인
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[1]/input').send_keys(email)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[2]/input').send_keys(pwd)
    driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[3]/button').click()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(3)
    driver.implicitly_wait(5)

    driver.get("https://watcha.com/manage_profiles")

    time.sleep(3)
    driver.implicitly_wait(5)

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('css-1blyq76')

    for profile in profiles:
        print(profile.text)
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)
