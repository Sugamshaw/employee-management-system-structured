
const API = '/api/employees';
const qs = s => document.querySelector(s);

const state = { employees: [], filtered: [], pendingDeleteId: null, editingId: null };

function money(x){ return Number(x).toLocaleString('en-IN'); }
function fmtDate(d){ return d; }

async function req(url, opts={}){
  const res = await fetch(url, { headers:{'Content-Type':'application/json'}, ...opts });
  if(!res.ok){ const err = await res.json().catch(()=>({message:res.statusText})); alert(err.message||'Error'); throw new Error(); }
  return res.json();
}

async function load(){
  const data = await req(API);
  state.employees = data;
  state.filtered = [...data];
  render();
  loadDepartments();
}

function render(){
  const tbody = qs('#employeesTableBody');
  const rows = state.filtered.map(e => `<tr>
    <td>${e.id}</td>
    <td>${e.firstName} ${e.lastName}</td>
    <td>${e.email}</td>
    <td>${e.department}</td>
    <td>${money(e.salary)}</td>
    <td>${fmtDate(e.hireDate)}</td>
    <td>
      <button onclick="openEdit(${e.id})">Edit</button>
      <button onclick="confirmDelete(${e.id})">Delete</button>
    </td>
  </tr>`).join('');
  tbody.innerHTML = rows;
  qs('#noEmployeesMessage').classList.toggle('hidden', state.filtered.length !== 0);
  qs('#employeesTable').style.display = state.filtered.length ? 'table' : 'none';
}

async function loadDepartments(){
  const depts = await req(API + '/departments');
  const sel = qs('#departmentFilter');
  sel.innerHTML = '<option value="">All Departments</option>' + depts.map(d=>`<option>${d}</option>`).join('');
}

qs('#searchBtn').addEventListener('click', ()=> search(qs('#searchInput').value));
qs('#clearFilters').addEventListener('click', ()=>{ qs('#searchInput').value=''; qs('#departmentFilter').value=''; state.filtered=[...state.employees]; render(); });
qs('#departmentFilter').addEventListener('change', e=>{
  const v = e.target.value;
  state.filtered = v ? state.employees.filter(x=>x.department===v) : [...state.employees];
  render();
});

function search(keyword){
  keyword = keyword.trim();
  if(!keyword){ state.filtered=[...state.employees]; render(); return; }
  req(API + '/search?keyword=' + encodeURIComponent(keyword)).then(list=>{
    state.filtered = list; render();
  }).catch(()=>{});
}

function openModal(edit=false){
  qs('#employeeModal').classList.remove('hidden');
  qs('#modalTitle').textContent = edit ? 'Edit Employee' : 'Add Employee';
  qs('#saveBtn').textContent = edit ? 'Update' : 'Save';
}
function closeModal(){ qs('#employeeModal').classList.add('hidden'); state.editingId=null; qs('#employeeForm').reset(); }
qs('#addEmployeeBtn').addEventListener('click', ()=>{ openModal(false); });

qs('#cancelBtn').addEventListener('click', closeModal);
qs('#employeeForm').addEventListener('submit', async (ev)=>{
  ev.preventDefault();
  const payload = {
    firstName: qs('#firstName').value.trim(),
    lastName: qs('#lastName').value.trim(),
    email: qs('#email').value.trim(),
    department: qs('#department').value,
    salary: parseFloat(qs('#salary').value),
    hireDate: qs('#hireDate').value
  };
  if(state.editingId){
    await req(`${API}/${state.editingId}`, { method:'PUT', body: JSON.stringify(payload)});
  }else{
    await req(API, { method:'POST', body: JSON.stringify(payload)});
  }
  closeModal();
  load();
});

window.openEdit = (id)=>{
  const e = state.employees.find(x=>x.id===id);
  if(!e) return;
  state.editingId = id;
  qs('#firstName').value = e.firstName;
  qs('#lastName').value = e.lastName;
  qs('#email').value = e.email;
  qs('#department').value = e.department;
  qs('#salary').value = e.salary;
  qs('#hireDate').value = e.hireDate;
  openModal(true);
};

window.confirmDelete = (id)=>{
  state.pendingDeleteId = id;
  qs('#confirmMessage').textContent = 'Delete employee #' + id + '?';
  qs('#confirmModal').classList.remove('hidden');
};
qs('#confirmCancel').addEventListener('click', ()=> qs('#confirmModal').classList.add('hidden'));
qs('#confirmOk').addEventListener('click', async ()=>{
  const id = state.pendingDeleteId;
  if(id!=null){
    await req(`${API}/${id}`, { method:'DELETE' });
    state.pendingDeleteId = null;
    qs('#confirmModal').classList.add('hidden');
    load();
  }
});

load();
