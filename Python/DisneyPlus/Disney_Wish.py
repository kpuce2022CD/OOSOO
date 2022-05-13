import time
from selenium.webdriver.support import expected_conditions as EC
from bs4 import BeautifulSoup
from selenium.webdriver.support.wait import WebDriverWait
from Disney_Login import driver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지

#!!execute just for login!!
#exec(open("Disney_Login.py", encoding='UTF-8').read())
print("찜목록 불러오는 중...")

driver.get("https://www.disneyplus.com/watchlist")
wishlist = []

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

print(wishlist)

#driver.quit()