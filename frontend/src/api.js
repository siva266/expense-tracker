const BASE = "http://localhost:8081/api/expenses";
async function handle(res) {
  if (res.status === 204) return null;
  const data = await res.json();
  if (!res.ok) throw data;
  return data;
}
export const getExpenses = (p) =>
  fetch(`${BASE}?${new URLSearchParams(p)}`).then(handle);
export const getSummary = () => fetch(`${BASE}/summary`).then(handle);
export const saveExpense = (e, id) =>
  fetch(id ? `${BASE}/${id}` : BASE, {
    method: id ? "PUT" : "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(e),
  }).then(handle);
export const deleteExpense = (id) =>
  fetch(`${BASE}/${id}`, { method: "DELETE" }).then(handle);
