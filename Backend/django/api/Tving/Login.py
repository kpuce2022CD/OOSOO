# selenium 불러오기
from selenium.webdriver import ActionChains
import pickle
import glob
import time

def t_login(email, pwd, name, driver):
    # 쿠키가 있는지 체크
    find_cookie = False
    cookie_files = glob.glob('./api/Cookies/' + email + '_t.pkl')
    if len(cookie_files) > 0:
        find_cookie = True
    login_with_cookie = False
    url = 'https://www.tving.com/'

    while (not login_with_cookie):
        if find_cookie:  # 쿠키 정보를 이용해 로그인
            try:
                cookies = pickle.load(
                    open('./api/Cookies/' + email + '_t.pkl', "rb"))
                driver.get(url)
                for c in cookies:
                    driver.add_cookie(c)
                driver.get(url)
                login_with_cookie = True
            except:
                return "login error"

        else:  # 쿠키가 없을 경우, 수동으로 로그인하여 쿠키 정보 보존
            try:
                driver.get(
                    'https://user.tving.com/pc/user/otherLogin.tving?loginType=20&from=pc&rtUrl=https%3A%2F%2Fwww.tving.com&csite=&isAuto=false')

                # 로그인 액션체인
                id_box = driver.find_element_by_xpath('//*[@id="a"]')
                passwd_box = driver.find_element_by_xpath('//*[@id="b"]')
                login_button = driver.find_element_by_xpath('//*[@id="doLoginBtn"]')

                login_act = ActionChains(driver)
                login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(
                    login_button).perform()

                time.sleep(2)
                driver.implicitly_wait(5)

                #######################프로필 선택이 필요 없을 경우도 있음. 예외 처리 필요###########################
                # 프로필 선택 화면 로딩이 완료될때까지 대기
                driver.get("https://user.tving.com/pc/user/profiles.tving")
                time.sleep(2)
                driver.implicitly_wait(5)

                # 프로필 선택
                profiles = driver.find_elements_by_class_name('item')
                for profile in profiles:
                    if profile.text == name:
                        profile.click()
                        break

                time.sleep(2)
                driver.implicitly_wait(5)

                driver.add_cookie({"name": email, "value": email})

            except:
                return "login error"

            while (not find_cookie):
                cookies = driver.get_cookies()
                if len(cookies) > 0:
                    find_cookie = True
            pickle.dump(cookies, open('./api/Cookies/' + email + '_t.pkl', "wb"))
            return "normal login"

    return "cookie login"