import { useState } from 'react';
import { candidateService } from '../api/candidateService';
import type {CandidateRequest} from '../types';

interface Props {
    onCreated: () => void;
}

export default function CandidateForm({ onCreated }: Props) {
    const [form, setForm] = useState<CandidateRequest>({
        fullName: '',
        dateOfBirth: '',
        contactNumber: '',
        email: '',
        skills: []
    });

    const handleSubmit = async (e: React.SubmitEvent) => {
        e.preventDefault();
        await candidateService.create(form);
        onCreated();
        setForm({ fullName: '', dateOfBirth: '', contactNumber: '', email: '', skills: [] });
    };

    return (
        <form onSubmit={handleSubmit}>
            <h3>Add New Candidate</h3>
            <input placeholder="Full Name" value={form.fullName} required
                   onChange={e => setForm({...form, fullName: e.target.value})} />

            <input type="date" value={form.dateOfBirth} required
                   onChange={e => setForm({...form, dateOfBirth: e.target.value})} />

            <input placeholder="Email" type="email" value={form.email} required
                   onChange={e => setForm({...form, email: e.target.value})} />

            <input placeholder="Phone" value={form.contactNumber}
                   onChange={e => setForm({...form, contactNumber: e.target.value})} />

            <button type="submit">Create Candidate</button>
        </form>
    );
}