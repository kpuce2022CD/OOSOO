# selenium 불러오기
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
import time


def n_login(email, pwd, name, driver):
    try:
        # 실행할 웹페이지 불러오기(넷플릭스)
        driver.get('https://www.netflix.com/kr/login')

        # 로그인 자동화
        id = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.XPATH, '//*[@id="id_userLoginId"]')))
        id.send_keys(email)

        passwd = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.XPATH, '//*[@id="id_password"]')))
        passwd.send_keys(pwd)

        driver.find_element(By.XPATH, '//*[@id="appMountPoint"]/div/div[3]/div/div/div[1]/form/button').click()


        # 프로필 선택 화면 로딩이 완료될때까지 대기
        time.sleep(2)
        driver.implicitly_wait(5)
        print(driver.current_url)

        driver.get('https://www.netflix.com/profiles')

        time.sleep(2)
        driver.implicitly_wait(5)
        print(driver.current_url)

        # 프로필 선택
        userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

        for profile in userProfiles:
            print(profile.text)
            if profile.text == name:
                profile.click()
                break

        time.sleep(2)
        driver.implicitly_wait(5)
        return True

    except:
        return False
