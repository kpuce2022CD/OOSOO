# selenium 불러오기
from selenium.webdriver.common.by import By
import time
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait

def d_login(email, pwd, name, driver):
    try:
        driver.get('https://www.disneyplus.com/ko-kr/login/')
        time.sleep(2)
        driver.implicitly_wait(5)

        # 로그인 자동화
        input_email = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.CSS_SELECTOR, '#email')))
        input_email.send_keys(email)
        input_email.send_keys(Keys.RETURN)
        time.sleep(3)
        driver.implicitly_wait(5)

        input_pwd = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.CSS_SELECTOR, '#password')))
        input_pwd.send_keys(pwd)
        input_pwd.send_keys(Keys.RETURN)
        time.sleep(3)
        driver.implicitly_wait(5)

        # 리스트 출력 후 선택 방식
        userProfiles = driver.find_elements(By.XPATH, '//*[@id="remove-main-padding_index"]/div/div/section/ul/div')

        for profile in userProfiles:
            if profile.text.strip() == name:
                profile.click()
                break

        time.sleep(2)
        driver.implicitly_wait(5)
        return True

    except:
        return False
