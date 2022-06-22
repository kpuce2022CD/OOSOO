# selenium 불러오기
import glob
import pickle
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
import time


def n_login(email, pwd, name, driver):
    # 쿠키가 있는지 체크
    find_cookie = False
    cookie_files = glob.glob('./api/Cookies/' + email + '_n.pkl')
    if len(cookie_files) > 0:
        find_cookie = True
    login_with_cookie = False
    url = 'https://www.netflix.com/browse'

    while (not login_with_cookie):
        if find_cookie:  # 쿠키 정보를 이용해 로그인
            try:
                cookies = pickle.load(
                    open('./api/Cookies/' + email + '_n.pkl', "rb"))
                driver.get(url)
                for c in cookies:
                    driver.add_cookie(c)
                driver.get(url)
                login_with_cookie = True
                time.sleep(1)
                driver.implicitly_wait(5)

                # 프로필 선택
                userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

                for profile in userProfiles:
                    print(profile.text)
                    if profile.text == name:
                        profile.click()
                        break

            except:
                return False


        else:  # 쿠키가 없을 경우, 수동으로 로그인하여 쿠키 정보 보존
            try:
                # 실행할 웹페이지 불러오기(넷플릭스)
                driver.get('https://www.netflix.com/kr/login')

                # 로그인 자동화
                id = WebDriverWait(driver, 16).until(
                    EC.presence_of_element_located((By.XPATH, '//*[@id="id_userLoginId"]')))
                id.send_keys(email)

                passwd = WebDriverWait(driver, 16).until(
                    EC.presence_of_element_located((By.XPATH, '//*[@id="id_password"]')))
                passwd.send_keys(pwd)

                driver.find_element(By.XPATH, '//*[@id="appMountPoint"]/div/div[3]/div/div/div[1]/form/button').click()

                # 프로필 선택 화면 로딩이 완료될때까지 대기
                time.sleep(2)
                driver.implicitly_wait(5)

                driver.get('https://www.netflix.com/profiles')

                time.sleep(2)
                driver.implicitly_wait(5)

                # 프로필 선택
                userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

                for profile in userProfiles:
                    print(profile.text)
                    if profile.text == name:
                        profile.click()
                        break

                time.sleep(2)
                driver.implicitly_wait(5)

            except:
                return False

            while (not find_cookie):
                cookies = driver.get_cookies()
                if len(cookies) > 0:
                    find_cookie = True
            pickle.dump(cookies, open('./api/Cookies/' + email + '_n.pkl', "wb"))
            return "normal login"

    return True
