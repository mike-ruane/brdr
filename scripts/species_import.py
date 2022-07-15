import psycopg2
import urllib.parse as urlparse
import os
from pathlib import Path

url = urlparse.urlparse(os.environ['DATABASE_URL'])
dbname = url.path[1:]
user = url.username
password = url.password
host = url.hostname
port = url.port

conn = psycopg2.connect(
    dbname=dbname,
    user=user,
    password=password,
    host=host,
    port=port
)

cur = conn.cursor()

path = Path(__file__).parent / "../data/species.csv"
with path.open() as file:
    next(file)
    cur.copy_from(file, 'species', columns=('scientific_name', 'preferred_common_name', 'habitat', 'genus', 'family', 'family_order', 'breeding_population', 'winter_visitor_population'), sep=',', null='')

conn.commit()
cur.close()
conn.close()
