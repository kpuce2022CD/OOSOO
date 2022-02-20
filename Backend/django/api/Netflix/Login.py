# selenium 불러오기
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time


def n_login(email, pwd, name, driver):
    # 실행할 웹페이지 불러오기(넷플릭스)
    driver.get('https://www.netflix.com/kr/login')

    # 로그인 자동화
    driver.find_element_by_xpath('//*[@id="id_userLoginId"]').send_keys(email)
    driver.find_element_by_xpath('//*[@id="id_password"]').send_keys(pwd)
    driver.find_element_by_xpath('//*[@id="appMountPoint"]/div/div[3]/div/div/div[1]/form/button').click()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(5)
    driver.implicitly_wait(10)
    print(driver.current_url)

    driver.get('https://www.netflix.com/profiles')

    time.sleep(3)
    driver.implicitly_wait(5)
    print(driver.current_url)

    # 프로필 선택
    userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

    for profile in userProfiles:
        print(profile.text)
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)
