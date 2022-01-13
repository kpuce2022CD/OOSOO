# Watcha 영화 목록(Title) 불러오기

# selenium 불러오기
from selenium import webdriver
import time

# chrome창(웹드라이버) 열기
driver = webdriver.Chrome()

# 실행할 웹페이지 불러오기(왓챠)
driver.get("https://watcha.com/explore")
driver.set_window_position(0, 0)
driver.set_window_size(1900, 1000)

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

movies = driver.find_elements_by_class_name('css-1g3awd')

for movie in movies:
    print(movie.text)
