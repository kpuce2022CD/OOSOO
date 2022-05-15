from selenium import webdriver
from pyvirtualdisplay import Display
from bs4 import BeautifulSoup
import time
from api.DisneyPlus.Login import d_login
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

def d_wishes(email, pwd, name):
    # display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    # display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    #로그인
    d_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    #<wishlist>
    wishlist = []

    driver.get("https://www.disneyplus.com/watchlist")

    try:
        WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.ID, "watchlist-collection")))

        # 페이지 스크롤
        last_height = driver.execute_script("return document.body.scrollHeight")

        while True:
            scroll_down = 0
            while scroll_down < 10:
                driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
                time.sleep(0.2)
                scroll_down += 1

            # 스크롤 내린 후 스크롤 높이 다시 가져옴
            new_height = driver.execute_script("return document.body.scrollHeight")
            if new_height == last_height:
                break
            last_height = new_height

        html = driver.page_source
        soup = BeautifulSoup(html, 'html.parser')

        wishes = soup.select('#watchlist-collection > div > div > div > div > div')

        for wish in wishes:
            wishlist.append(wish.select('a > div')[0].get('aria-label'))
    except:
        print("페이지 로드에 오류가 발생했습니다.")
    finally:
        print(wishlist)

    driver.quit()
    # display.stop()

    return wishlist