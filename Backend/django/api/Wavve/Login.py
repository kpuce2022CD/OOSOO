# selenium 불러오기
from selenium.webdriver import ActionChains
from selenium.webdriver.common.by import By
import pickle
import glob
import time

def wav_login(email, pwd, name, driver):
    #쿠키가 있는지 체크
    find_cookie = False
    cookie_files = glob.glob('./api/Cookies/'+email+'_wav.pkl')
    if len(cookie_files) > 0:
        find_cookie = True
    login_with_cookie = False
    url = 'https://www.wavve.com/'

    while(not login_with_cookie):
        if find_cookie:  # 쿠키 정보를 이용해 로그인
            cookies = pickle.load(open('./api/Cookies/'+email+'_wav.pkl', "rb"))
            driver.get(url)
            for c in cookies:
                driver.add_cookie(c)
            driver.get(url)
            login_with_cookie = True

        else: #쿠키가 없을 경우, 수동으로 로그인하여 쿠키 정보 보존
            try:
                driver.get('https://www.wavve.com/member/login')

                # 로그인 액션체인
                id_box = driver.find_element_by_css_selector(
                    '#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(1) > label > input')
                passwd_box = driver.find_element_by_css_selector(
                    '#app > div.body > main > div > div.join-sns-box > form > fieldset > ul.input-wrap01 > li:nth-child(2) > label > input')
                login_button = driver.find_element_by_css_selector(
                    '#app > div.body > main > div > div.join-sns-box > form > fieldset > div > a')

                login_act = ActionChains(driver)
                login_act.send_keys_to_element(id_box, email).send_keys_to_element(passwd_box, pwd).click(
                    login_button).perform()

                time.sleep(2)
                driver.implicitly_wait(5)

                # 프로필 선택 화면 로딩이 완료될때까지 대기
                driver.get("https://www.wavve.com/member/profile_list?referer=%2Findex.html")
                time.sleep(2)
                driver.implicitly_wait(5)

                # 프로필 선택
                profiles = driver.find_elements_by_class_name('user-style')
                for profile in profiles:
                    if profile.text == name:
                        profile.click()
                        break

                time.sleep(2)
                driver.implicitly_wait(5)
                xbox = driver.find_element(By.XPATH, '//*[@id="contents"]/div[1]/section/div[1]')
                xbox.click()
                driver.add_cookie({"name": email, "value": email})

            except:
                return "login error"

            while(not find_cookie):
                cookies = driver.get_cookies()
                if len(cookies) > 0:
                    find_cookie = True
            pickle.dump(cookies, open('./api/Cookies/'+email+'_wav.pkl', "wb"))
            driver.close()
            driver.quit()
            return "normal login"

    return "cookie login"
