# selenium 불러오기
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time


def n_login(email, pwd, name, driver):
    # 실행할 웹페이지 불러오기(넷플릭스)
    driver.get('https://www.netflix.com/kr/login')

    # 로그인 자동화
    driver.find_element(By.NAME, 'userLoginId').send_keys(email)
    driver.find_element(By.NAME, 'password').send_keys(pwd)
    driver.find_element(By.CSS_SELECTOR, '#appMountPoint > div > div.login-body > div > div > div.hybrid-login-form-main > form > button').click()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "profile-name")))

    # 프로필 선택
    userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

    for profile in userProfiles:
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)
