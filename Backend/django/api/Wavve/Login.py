# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

"""일반 로그인 wavve // 간편로그인 KAKAO, SKT, NAVER, FACEBOOK, APPLE
    웨이브, 카카오, SKT, 페북 간편로그인 자동화 가능 // 네이버, 애플은 예외 있음. 보안코드입력"""
def wav_login(email, pwd, name, driver):

    driver.get('https://www.wavve.com/member/login')

    # 로그인 액션체인
    id_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(1) > label > input')
    passwd_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(2) > label > input')
    #login_auto_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.checkbox-wrap01 > li:nth-child(2) > label')
    login_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > div > a')

    login_act = ActionChains(driver)
    login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_button).perform()

    time.sleep(3)
    driver.implicitly_wait(5)

    #######################프로필 선택이 필요 없을 경우도 있음. 예외 처리 필요###########################
    # 프로필 선택 화면 로딩이 완료될때까지 대기
    driver.get("https://www.wavve.com/member/profile_list?referer=%2Findex.html")
    time.sleep(3)
    driver.implicitly_wait(5)

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('user-style')
    for profile in profiles:
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)

