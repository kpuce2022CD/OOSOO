from Netflix_Login import driver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지
import time

#!!execute just for login!!
#exec(open("Netflix_Login.py", encoding='UTF-8').read())
print("시청중인 목록 불러오는 중...")

driver.get("https://www.netflix.com/browse/continue-watching")
watchings = driver.find_elements(By.CLASS_NAME, 'fallback-text')
time.sleep(1)

print("        < 시청중인 목록 >        ")
print("------------------------------")
for content in watchings:
    print(content.text)
print("------------------------------")

driver.quit()