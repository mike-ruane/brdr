import json
import geojson
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

path = Path(__file__).parent / "../data/ireland-counties.json"
with path.open() as file:
    gj = geojson.load(file)
    for feature in gj['features']:
        name=feature['properties']['name']
        geo=(json.dumps(feature['geometry']))
        cur.execute('INSERT INTO geo_locations (name, geo) VALUES (%s, ST_GeomFromText(ST_AsText(ST_GeomFromGeoJSON(%s))))', (name, geo))

conn.commit()
cur.close()
conn.close()
