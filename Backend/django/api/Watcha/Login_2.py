# selenium 불러오기
import glob
import pickle

from selenium.webdriver import ActionChains
from selenium.webdriver.common.by import By
import time


def wat_login(email, pwd, name, driver):
    # 쿠키가 있는지 체크
    find_cookie = False
    cookie_files = glob.glob('./api/Cookies/' + email + '_w.pkl')
    if len(cookie_files) > 0:
        find_cookie = True
    login_with_cookie = False
    url = 'https://watcha.com/'

    while (not login_with_cookie):
        if find_cookie:  # 쿠키 정보를 이용해 로그인
            cookies = pickle.load(open('./api/Cookies/' + email + '_w.pkl', "rb"))
            driver.get(url)
            for c in cookies:
                driver.add_cookie(c)
            driver.get(url)
            login_with_cookie = True
            time.sleep(2)
            driver.implicitly_wait(5)

        else:  # 쿠키가 없을 경우, 수동으로 로그인하여 쿠키 정보 보존
            try:
                # 실행할 웹페이지 불러오기(왓챠 로그인 화면)
                driver.get("https://watcha.com/sign_in")

                # 로그인
                driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[1]/input').send_keys(
                    email)
                driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[2]/input').send_keys(
                    pwd)
                driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[3]/button').click()

                # 프로필 선택 화면 로딩이 완료될때까지 대기
                time.sleep(2)
                driver.implicitly_wait(5)

                driver.get("https://watcha.com/manage_profiles")

                time.sleep(2)
                driver.implicitly_wait(5)

                # 프로필 선택
                profiles = driver.find_elements_by_class_name('css-1blyq76')

                for profile in profiles:
                    print(profile.text)
                    if profile.text == name:
                        profile.click()
                        break

                time.sleep(2)
                driver.implicitly_wait(5)

            except:
                return "login error"

            while (not find_cookie):
                cookies = driver.get_cookies()
                if len(cookies) > 0:
                    find_cookie = True
            pickle.dump(cookies, open('./api/Cookies/' + email + '_w.pkl', "wb"))
            return "normal login"

    return "cookie login"
