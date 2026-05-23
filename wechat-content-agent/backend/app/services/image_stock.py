import httpx

from app.config import PEXELS_API_KEY


async def search_stock_images(keywords: list[str], count: int = 5) -> list[dict]:
    if not PEXELS_API_KEY:
        return []

    query = ",".join(keywords[:3])

    async with httpx.AsyncClient(timeout=10.0) as client:
        response = await client.get(
            "https://api.pexels.com/v1/search",
            headers={"Authorization": PEXELS_API_KEY},
            params={"query": query, "per_page": count},
        )
        response.raise_for_status()

    data = response.json()
    return [
        {
            "url": photo["src"]["large"],
            "thumbnail": photo["src"]["medium"],
            "photographer": photo["photographer"],
        }
        for photo in data.get("photos", [])
    ]
