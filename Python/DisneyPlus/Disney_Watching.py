import time

from Disney_Login import driver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지
from bs4 import BeautifulSoup

#!!execute just for login!!
#exec(open("Disney_Login.py", encoding='UTF-8').read())
print("시청중인 목록 불러오는 중...")

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

watching_list = []
#bs4 source 토스
html = driver.page_source
soup = BeautifulSoup(html, 'html.parser')

watchings = soup.select('#home-collection > div:nth-child(6) > div > div > div > div > div > div')

#cross-classify type : 분류
for watching in watchings:
    movie = watching.find("div", attrs={"data-program-type": "movie"})
    episode = watching.find("div", attrs={"data-program-type" : "episode"})

    if movie:
        watching_list.append(movie.select('#asset-metadata > h5')[0].text)
    elif episode:
        watching_list.append(episode.select('#asset-metadata > p')[0].text)

print(watching_list)
driver.quit()