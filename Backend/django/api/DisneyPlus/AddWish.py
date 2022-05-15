from selenium import webdriver
from pyvirtualdisplay import Display
import time
from api.DisneyPlus.Login import d_login
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys

def d_addwish(email, pwd, name, title):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

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


    driver.get("https://www.disneyplus.com/search")

    search_box = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.ID, "search-input")))
    search_box.send_keys(title)
    time.sleep(1)

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

    try:
        WebDriverWait(driver, 10).until(
            EC.presence_of_element_located((By.XPATH, '//*[@id="section_index"]/div/div/div[2]/section/div/div')))
        driver.find_element(By.XPATH, '//*[@data-testid="search-result-0"]').send_keys(Keys.ENTER)

        driver.implicitly_wait(10)
        driver.find_element(By.XPATH, '//*[@id="details_index"]/div/article/div[3]/div/div[2]/div/span/button').click()
    except:
        print("페이지 로드중에 오류가 발생했습니다.")
    finally:
        print("찜 추가/삭제 완료.")
        time.sleep(3)  # 디즈니는 버튼을 누르고 강제로 기다려야함 - 위시리스트 추가/삭제 동작이 프론트(웹)에서 진행되는 듯

    driver.quit()
    display.stop()

    return "success"