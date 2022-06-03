# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

def t_login(email, pwd, name, driver):
    driver.get('https://user.tving.com/pc/user/otherLogin.tving?loginType=20&from=pc&rtUrl=https%3A%2F%2Fwww.tving.com&csite=&isAuto=false')

    # 로그인 액션체인
    id_box = driver.find_element_by_css_selector('#a')
    passwd_box = driver.find_element_by_css_selector('#b')
    login_button = driver.find_element_by_css_selector('#doLoginBtn')

    login_act = ActionChains(driver)
    login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(
        login_button).perform()

    time.sleep(3)
    driver.implicitly_wait(5)

    #######################프로필 선택이 필요 없을 경우도 있음. 예외 처리 필요###########################
    # 프로필 선택 화면 로딩이 완료될때까지 대기
    driver.get("https://user.tving.com/pc/user/profiles.tving")
    time.sleep(3)
    driver.implicitly_wait(5)

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('item')
    for profile in profiles:
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)
    driver.save_screenshot("screenshot_tving.png")