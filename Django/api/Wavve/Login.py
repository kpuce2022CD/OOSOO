# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

"""일반 로그인 wavve // 간편로그인 KAKAO, SKT, NAVER, FACEBOOK, APPLE
    웨이브, 카카오, SKT, 네이버(예외) 완성 // 페북, 애플 미완성"""
def wav_login(login_way, email, pwd, name, driver):

    driver.get('https://www.wavve.com/member/login')

    if login_way == "skt": #SKT 간편로그인

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
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(
            login_button).perform()

        # 프로필 선택 화면 로딩이 완료될때까지 대기
        time.sleep(3)
        driver.implicitly_wait(5)
        WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, 'user-style')))

    elif login_way == 'wavve': #웨이브 기본로그인
        # 로그인 액션체인
        id_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(1) > label > input')
        passwd_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(2) > label > input')
        login_auto_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.checkbox-wrap01 > li:nth-child(2) > label')
        login_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > div > a')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_auto_button).click(login_button).perform()

        # 프로필 선택 화면 로딩이 완료될때까지 대기
        time.sleep(3)
        driver.implicitly_wait(5)
        WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, 'user-style')))

    elif login_way == 'kakao':
        # 카카오 로그인 화면
        driver.execute_script('window.scrollTo(0,600);')
        kakao_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[1]/a')
        window_before = driver.window_handles[0]
        kakao_login.click()
        time.sleep(2)
        driver.implicitly_wait(5)
        window_after = driver.window_handles[1]

        driver.switch_to.window(window_after)
        id_box = driver.find_element_by_css_selector('#id_email_2_label > span:nth-child(1)')
        passwd_box = driver.find_element_by_css_selector('#id_password_3_label')
        # login_auto_button = driver.find_element_by_css_selector('#login-form > fieldset > div.set_login > div > label')
        login_button = driver.find_element_by_css_selector('#login-form > fieldset > div.wrap_btn > button.btn_g.btn_confirm.submit')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_button).perform()

        driver.switch_to.window(window_before)

    elif login_way == 'NAVER': ##자동입력방지 문자때문에 간편로그인 자동화가 안됨.
        # 네이버 로그인 화면
        driver.execute_script('window.scrollTo(0,600);')
        naver_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[3]/a')
        window_before = driver.window_handles[0]
        naver_login.click()
        time.sleep(2)
        driver.implicitly_wait(5)
        window_after = driver.window_handles[1]

        driver.switch_to.window(window_after)
        id_box = driver.find_element_by_css_selector('#id')
        passwd_box = driver.find_element_by_css_selector('#pw')
        # login_auto_button = driver.find_element_by_css_selector('#login_keep_wrap > div.keep_check > label')
        login_button = driver.find_element_by_css_selector('#log\.login')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_button).perform()

        driver.switch_to.window(window_before)

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('user-style')
    for profile in profiles:
        print(profile.text)
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)