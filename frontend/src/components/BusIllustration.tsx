import type { CSSProperties } from 'react'

const WINDOWS_X = [88, 150, 212, 274, 336, 398]
const ROAD_X = [0, 80, 160, 240, 320, 400, 480, 560]

function Spokes() {
  return (
    <g
      style={{
        animation: 'wheelSpin 1.5s linear infinite',
        transformBox: 'fill-box',
        transformOrigin: 'center',
      } as CSSProperties}
    >
      <circle r={14} fill="#4a5568" />
      {[0, 45, 90, 135].map((deg) => {
        const rad = (deg * Math.PI) / 180
        const d = 12
        return (
          <line
            key={deg}
            x1={-Math.cos(rad) * d} y1={-Math.sin(rad) * d}
            x2={Math.cos(rad) * d} y2={Math.sin(rad) * d}
            stroke="#94a3b8"
            strokeWidth={2.5}
            strokeLinecap="round"
          />
        )
      })}
      <circle r={4} fill="#e2e8f0" />
    </g>
  )
}

function Wheel({ cx, cy }: { cx: number; cy: number }) {
  return (
    <g transform={`translate(${cx},${cy})`}>
      <circle r={28} fill="#1a202c" />
      <circle r={21} fill="#2d3748" />
      <Spokes />
      <circle r={28} fill="none" stroke="#374151" strokeWidth={2} strokeDasharray="4 4" />
    </g>
  )
}

export default function BusIllustration() {
  return (
    <svg
      viewBox="0 0 520 230"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      className="w-full max-w-xl"
      aria-hidden="true"
    >
      <defs>
        <clipPath id="bmb-body">
          <rect x="25" y="44" width="468" height="120" rx="14" />
        </clipPath>
      </defs>

      {/* Road */}
      <rect x="0" y="196" width="520" height="34" fill="#1a2332" />
      <rect x="0" y="196" width="520" height="3" fill="#253347" />
      <g style={{ animation: 'roadMove 0.65s linear infinite' } as CSSProperties}>
        {ROAD_X.map((x) => (
          <rect key={x} x={x} y="208" width="46" height="4" rx="2" fill="#f6e05e" fillOpacity={0.5} />
        ))}
      </g>

      {/* Ground shadow */}
      <ellipse cx="260" cy="196" rx="198" ry="7" fill="rgba(0,0,0,0.2)" />

      {/* Bus — floating */}
      <g style={{ animation: 'busFloat 3s ease-in-out infinite' } as CSSProperties}>

        {/* 3D underside depth */}
        <rect x="60" y="162" width="390" height="11" rx="3" fill="#0d1520" />

        {/* Main body */}
        <rect x="25" y="44" width="468" height="120" rx="14" fill="#f7fafc" />

        {/* Green top stripe */}
        <rect x="25" y="44" width="468" height="26" fill="#0e9e4d" clipPath="url(#bmb-body)" />
        <rect x="25" y="44" width="468" height="26" fill="rgba(255,255,255,0.1)" clipPath="url(#bmb-body)" />

        {/* Lower body panel */}
        <rect x="25" y="132" width="468" height="32" fill="#dde6ef" clipPath="url(#bmb-body)" />

        {/* Divider lines */}
        <line x1="25" y1="70" x2="493" y2="70" stroke="#0a7a3a" strokeWidth={1.5} />
        <line x1="25" y1="132" x2="493" y2="132" stroke="#bfccd9" strokeWidth={1} />

        {/* Top body sheen */}
        <rect x="25" y="44" width="468" height="5" fill="rgba(255,255,255,0.22)" clipPath="url(#bmb-body)" />

        {/* ── Front ── */}
        <rect x="30" y="74" width="52" height="54" rx="5" fill="#1a202c" />
        <rect x="33" y="77" width="15" height="23" rx="3" fill="rgba(255,255,255,0.07)" />

        {/* Headlight + glow */}
        <rect x="28" y="136" width="28" height="10" rx="3" fill="#fef08a" />
        <ellipse cx="42" cy="141" rx="28" ry="12" fill="#fef08a" opacity={0.15} />

        {/* Front bumper */}
        <rect x="25" y="150" width="60" height="12" rx="4" fill="#94a3b8" />

        {/* Grille */}
        <rect x="28" y="150" width="46" height="8" rx="2" fill="#64748b" />
        {[38, 52, 66].map((x) => (
          <line key={x} x1={x} y1={150} x2={x} y2={158} stroke="#94a3b8" strokeWidth={1.5} />
        ))}

        {/* Side mirror */}
        <line x1="18" y1="93" x2="27" y2="91" stroke="#64748b" strokeWidth={2} />
        <rect x="6" y="88" width="14" height="9" rx="3" fill="#94a3b8" />

        {/* ── Windows ── */}
        {WINDOWS_X.map((x, i) => (
          <g key={i}>
            <rect x={x} y={74} width={52} height={52} rx={5} fill="#263244" />
            <rect x={x} y={74} width={52} height={52} rx={5} fill="none" stroke="#dde6ef" strokeWidth={1} />
            <rect x={x + 4} y={78} width={13} height={22} rx={3} fill="rgba(255,255,255,0.06)" />
            <line x1={x + 26} y1={74} x2={x + 26} y2={126} stroke="#364860" strokeWidth={1.5} />
          </g>
        ))}

        {/* ── Rear ── */}
        <rect x="453" y="74" width="37" height="54" rx="5" fill="#1a202c" />
        <rect x="456" y="77" width="10" height="23" rx="3" fill="rgba(255,255,255,0.05)" />

        {/* Tail light + glow */}
        <rect x="461" y="136" width="26" height="10" rx="3" fill="#f87171" />
        <ellipse cx="474" cy="141" rx="28" ry="12" fill="#f87171" opacity={0.12} />

        {/* Rear bumper */}
        <rect x="453" y="150" width="40" height="12" rx="4" fill="#94a3b8" />

        {/* ── Roof AC unit ── */}
        <rect x="165" y="34" width="150" height="12" rx="4" fill="#dde6ef" />
        <rect x="170" y="31" width="140" height="5" rx="2" fill="#c8d5e2" />
        {[0, 1, 2, 3, 4, 5, 6, 7].map((i) => (
          <line key={i} x1={175 + i * 17} y1={31} x2={175 + i * 17} y2={36} stroke="#94a3b8" strokeWidth={1.5} />
        ))}

        {/* Number plate */}
        <rect x="198" y="150" width="110" height="12" rx="3" fill="#1a202c" />
        <rect x="201" y="152" width="104" height="8" rx="2" fill="#fefce8" />

        {/* ── Wheels ── */}
        <Wheel cx={112} cy={172} />
        <Wheel cx={406} cy={172} />
      </g>
    </svg>
  )
}
