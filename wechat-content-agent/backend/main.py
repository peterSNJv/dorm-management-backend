from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from fastapi.staticfiles import StaticFiles

from app.api.content import router as content_router

app = FastAPI(title="公众号内容生成 Agent", version="1.0.0")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["http://localhost:5173"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.mount("/static", StaticFiles(directory="static"), name="static")

app.include_router(content_router, prefix="/api/content")


@app.get("/health")
async def health_check():
    return {"status": "ok"}
