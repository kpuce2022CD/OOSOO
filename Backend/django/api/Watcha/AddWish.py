# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from pyvirtualdisplay import Display
from api.Watcha.Login import w_login
import time


def w_addwish(email, pwd, name, title):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (AWS 경로 : "/home/ubuntu/django_server/chromedriver")
    driver = webdriver.Chrome("/home/ubuntu/django_server/chromedriver")  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"

    # 왓챠 로그인
    w_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    # 해당 영화 검색
    driver.get("https://watcha.com/browse/search")

    search = driver.find_element_by_xpath('//*[@id="query"]')
    search.send_keys(title)
    time.sleep(1)
    search.send_keys(Keys.RETURN)

    time.sleep(3)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1u94e99")))

    results = driver.find_elements_by_class_name('css-1u94e99')

    for result in results:
        print(result.text)
        if result.text == title:
            result.click()
            time.sleep(2)
            try:
                driver.find_element_by_class_name('css-zuimwg').click()
                time.sleep(2)
                driver.quit()
                display.stop()
                return "success"
            except:
                driver.quit()
                display.stop()
                return "fail"
        else:
            continue

    driver.quit()
    display.stop()

    return "fail"
