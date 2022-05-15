# selenium 불러오기
from selenium.webdriver.common.by import By
import time

def d_login(email, pwd, name, driver):
    driver.get('https://www.disneyplus.com/login/')
    driver.implicitly_wait(10)

    # 로그인 자동화
    driver.find_element(By.NAME, 'email').send_keys(email)
    driver.find_element(By.NAME, 'dssLoginSubmit').click()
    driver.implicitly_wait(5)

    driver.find_element(By.NAME, 'password').send_keys(pwd)
    driver.find_element(By.NAME, 'dssLoginSubmit').click()
    driver.implicitly_wait(5)

    # 리스트 출력 후 선택 방식
    userProfiles = driver.find_elements(By.XPATH, '//*[@id="remove-main-padding_index"]/div/div/section/ul/div')

    for profile in userProfiles:
        if profile.text.strip() == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)