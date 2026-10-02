import { useEffect, useState } from "react";
import { getExpenses, getSummary, saveExpense, deleteExpense } from "./api";

const CATEGORIES = ["Food", "Travel", "Shopping", "Bills", "Health", "Entertainment", "Other"];
const empty = { title: "", amount: "", category: "Food", date: new Date().toISOString().slice(0, 10), description: "" };

export default function App() {
  const [data, setData] = useState({ content: [], totalPages: 0 });
  const [summary, setSummary] = useState({});
  const [form, setForm] = useState(empty);
  const [editId, setEditId] = useState(null);
  const [errors, setErrors] = useState({});
  const [filters, setFilters] = useState({ category: "", from: "", to: "" });
  const [page, setPage] = useState(0);

  const load = async () => {
    const params = { page, size: 5 };
    Object.entries(filters).forEach(([k, v]) => v && (params[k] = v));
    setData(await getExpenses(params));
    setSummary(await getSummary());
  };
  useEffect(() => { load().catch(console.error); }, [page, filters]);

  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });
  const submit = async (e) => {
    e.preventDefault();
    try {
      await saveExpense({ ...form, amount: Number(form.amount) }, editId);
      setForm(empty); setEditId(null); setErrors({}); load();
    } catch (err) { setErrors(err.errors || { form: err.message }); }
  };
  const edit = (x) => { setEditId(x.id); setForm({ ...x, description: x.description || "" }); window.scrollTo(0, 0); };
  const remove = async (id) => { if (confirm("Delete this expense?")) { await deleteExpense(id); load(); } };
  const setFilter = (e) => { setPage(0); setFilters({ ...filters, [e.target.name]: e.target.value }); };
  const total = Object.values(summary).reduce((a, b) => a + Number(b), 0);

  return (
    <div className="container">
      <h1>💰 Expense Tracker</h1>

      <div className="cards">
        <div className="card"><small>Total Spent</small><b>₹{total.toFixed(2)}</b></div>
        {Object.entries(summary).map(([c, v]) => (
          <div className="card" key={c}><small>{c}</small><b>₹{Number(v).toFixed(2)}</b></div>
        ))}
      </div>

      <form onSubmit={submit} className="panel">
        <h3>{editId ? "Edit Expense" : "Add Expense"}</h3>
        {errors.form && <p className="err">{errors.form}</p>}
        <div className="grid">
          <label>Title<input name="title" value={form.title} onChange={change} />{errors.title && <span className="err">{errors.title}</span>}</label>
          <label>Amount<input name="amount" type="number" step="0.01" value={form.amount} onChange={change} />{errors.amount && <span className="err">{errors.amount}</span>}</label>
          <label>Category<select name="category" value={form.category} onChange={change}>{CATEGORIES.map((c) => <option key={c}>{c}</option>)}</select></label>
          <label>Date<input name="date" type="date" value={form.date} onChange={change} />{errors.date && <span className="err">{errors.date}</span>}</label>
          <label className="wide">Description<input name="description" value={form.description} onChange={change} /></label>
        </div>
        <button type="submit">{editId ? "Update" : "Add"}</button>
        {editId && <button type="button" className="ghost" onClick={() => { setEditId(null); setForm(empty); }}>Cancel</button>}
      </form>

      <div className="panel">
        <h3>Filter</h3>
        <div className="grid">
          <label>Category<select name="category" value={filters.category} onChange={setFilter}><option value="">All</option>{CATEGORIES.map((c) => <option key={c}>{c}</option>)}</select></label>
          <label>From<input type="date" name="from" value={filters.from} onChange={setFilter} /></label>
          <label>To<input type="date" name="to" value={filters.to} onChange={setFilter} /></label>
        </div>
      </div>

      <table>
        <thead><tr><th>Date</th><th>Title</th><th>Category</th><th>Amount</th><th></th></tr></thead>
        <tbody>
          {data.content.map((x) => (
            <tr key={x.id}>
              <td>{x.date}</td><td>{x.title}</td><td><span className="tag">{x.category}</span></td><td>₹{x.amount}</td>
              <td><button className="ghost" onClick={() => edit(x)}>Edit</button><button className="danger" onClick={() => remove(x.id)}>Delete</button></td>
            </tr>
          ))}
          {data.content.length === 0 && <tr><td colSpan="5" className="empty">No expenses found</td></tr>}
        </tbody>
      </table>

      <div className="pager">
        <button className="ghost" disabled={page === 0} onClick={() => setPage(page - 1)}>Prev</button>
        <span>Page {page + 1} of {Math.max(data.totalPages, 1)}</span>
        <button className="ghost" disabled={page + 1 >= data.totalPages} onClick={() => setPage(page + 1)}>Next</button>
      </div>
    </div>
  );
}
