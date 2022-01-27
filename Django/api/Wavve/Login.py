# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

def wav_login(email, pwd, name, driver):
    driver.get('https://www.wavve.com/member/login')

    # skt 로그인 화면
    driver.execute_script('window.scrollTo(0,600);')
    skt_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[2]/a/span[1]')
    skt_login.click()
    time.sleep(2)
    driver.implicitly_wait(5)

    # 로그인 액션체인
    id_box = driver.find_element_by_css_selector('#userId')
    passwd_box = driver.find_element_by_css_selector('#password')
    login_button = driver.find_element_by_css_selector('#content > div:nth-child(3) > div.content-bottom > div:nth-child(1)')

    login_act = ActionChains(driver)
    login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_button).perform()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, 'user-style')))

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('user-style')
    for profile in profiles:
        print(profile.text)
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)