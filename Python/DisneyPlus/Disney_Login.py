from selenium import webdriver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지
import time


#driver load
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = webdriver.Chrome(options=options)

driver.get('https://www.disneyplus.com/login/')
driver.implicitly_wait(10)

#로그인 자동화
driver.find_element(By.NAME, 'email').send_keys("lantern50@tukorea.ac.kr")
driver.find_element(By.NAME, 'dssLoginSubmit').click()
driver.implicitly_wait(5)

driver.find_element(By.NAME, 'password').send_keys("gPwls0228@")
driver.find_element(By.NAME, 'dssLoginSubmit').click()
driver.implicitly_wait(5)

#리스트 출력 후 선택 방식

userProfiles = driver.find_elements(By.XPATH, '//*[@id="remove-main-padding_index"]/div/div/section/ul/div')

for profile in userProfiles:
    if profile.text.strip() == "oosoo":  # 죄측 큰따옴표 안에 회원 프로필명 입력
        profile.click()
        break
time.sleep(1)
####로그인만 테스트 해볼 때 해제해주세요.#####
#driver.quit()
