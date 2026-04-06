import type {CandidateResponse} from '../types';
import { candidateService } from '../api/candidateService';

interface Props {
    candidates: CandidateResponse[];
    refreshList: () => void;
}

export default function CandidateList({ candidates, refreshList }: Props) {

    const handleDelete = async (id: number) => {
        if (window.confirm("Are you sure?")) {
            await candidateService.delete(id);
            refreshList();
        }
    };

    const handleAddSkill = async (id: number) => {
        const skill = prompt("Enter skill name:");
        if (skill) {
            await candidateService.addSkill(id, skill);
            refreshList();
        }
    };

    const handleRemoveSkill = async (id: number, skill: string) => {
        await candidateService.removeSkill(id, skill);
        refreshList();
    };

    if (candidates.length === 0) return <p>No candidates found.</p>;

    return (
        <table border={1} style={{ width: '100%', textAlign: 'left', marginTop: '20px' }}>
            <thead>
            <tr>
                <th>Full Name</th>
                <th>Email</th>
                <th>Skills</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            {candidates.map(candidate => (
                <tr key={candidate.id}>
                    <td>{candidate.fullName}</td>
                    <td>{candidate.email}</td>
                    <td>
                        {candidate.skills.map(skill => (
                            <span key={skill} style={{ marginRight: '5px', border: '1px solid #ccc', padding: '2px' }}>
                                    {skill}
                                <button onClick={() => handleRemoveSkill(candidate.id, skill)} style={{ marginLeft: '4px', cursor: 'pointer' }}>x</button>
                                </span>
                        ))}
                        <button onClick={() => handleAddSkill(candidate.id)}>+</button>
                    </td>
                    <td>
                        <button onClick={() => handleDelete(candidate.id)}>Delete Candidate</button>
                    </td>
                </tr>
            ))}
            </tbody>
        </table>
    );
}