# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

"""일반 로그인 wavve // 간편로그인 KAKAO, SKT, NAVER, FACEBOOK, APPLE
    웨이브, 카카오, SKT, 페북 간편로그인 자동화 가능 // 네이버, 애플은 예외 있음. 보안코드입력"""
def wav_login(login_way, email, pwd, name, driver):

    driver.get('https://www.wavve.com/member/login')

    if login_way == 'wavve': #웨이브 기본로그인
        # 로그인 액션체인
        id_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(1) > label > input')
        passwd_box = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(2) > label > input')
        login_auto_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.checkbox-wrap01 > li:nth-child(2) > label')
        login_button = driver.find_element_by_css_selector('#app > div.body > main > div > div.join-sns-box > form > fieldset > div > a')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_auto_button).click(login_button).perform()

    elif login_way == "skt": #SKT 간편로그인
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

    elif login_way == 'naver': ##자동입력방지 문자때문에 간편로그인 자동화가 안됨.
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
        #login_auto_button = driver.find_element_by_css_selector('#login_keep_wrap > div.keep_check > label')
        login_button = driver.find_element_by_css_selector('#log\.login')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_button).perform()

        driver.switch_to.window(window_before)

    elif login_way == 'facebook':
        # 페이스북 로그인 화면
        driver.execute_script('window.scrollTo(0,600);')
        fb_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[4]/a/span[1]')
        window_before = driver.window_handles[0]
        fb_login.click()
        time.sleep(2)
        driver.implicitly_wait(5)
        window_after = driver.window_handles[1]

        driver.switch_to.window(window_after)

        id_box = driver.find_element_by_css_selector('#email')
        passwd_box = driver.find_element_by_css_selector('#pass')
        login_auto_button = driver.find_element_by_css_selector('#offline_access')
        login_button = driver.find_element_by_css_selector('#loginbutton')

        login_act = ActionChains(driver)
        login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(login_auto_button).click(login_button).perform()

        driver.switch_to.window(window_before)

    elif login_way == 'apple': #애플로그인 첫 로그인시 브라우저 신뢰 보안코드 입력해야함
        # 애플 로그인 화면
        driver.execute_script('window.scrollTo(0,600);')
        apple_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[5]/a')
        apple_login.click()
        time.sleep(2)
        driver.implicitly_wait(5)

        id_box = driver.find_element_by_css_selector('#account_name_text_field')
        id_input_button = driver.find_element_by_xpath('//*[@id="sign-in"]')
        id_act = ActionChains(driver)
        id_act.send_keys_to_element(id_box, 'wendy0301@naver.com').click(id_input_button).perform()

        passwd_box = driver.find_element_by_css_selector('#password_text_field')
        passwd_input_button = driver.find_element_by_xpath('//*[@id="sign-in"]')
        passwd_act = ActionChains(driver)
        passwd_act.send_keys_to_element(passwd_box, 'Skscjswo7').click(passwd_input_button).perform()

        """애플 보안코드 입력해야함 이 구간에서
        신뢰체크버튼
        trust_button = driver.find_element_by_xpath('//*[@id="trust-browser-1644502826675-2"]')
        trust_button.click()"""

    #######################프로필 선택이 필요 없을 경우도 있음. 예외 처리 필요###########################
    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(3)
    driver.implicitly_wait(5)

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('user-style')
    for profile in profiles:
        print(profile.text)
        if profile.text == name:
            profile.click()
            break

    time.sleep(3)
    driver.implicitly_wait(5)