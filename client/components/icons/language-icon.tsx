import { Code2 } from "lucide-react";
import type { IconType } from "react-icons";

import {
  SiC,
  SiClojure,
  SiCplusplus,
  SiCrystal,
  SiCss,
  SiDart,
  SiDocker,
  SiElixir,
  SiErlang,
  SiFsharp,
  SiGnubash,
  SiGo,
  SiGraphql,
  SiHaskell,
  SiHtml5,
  SiJavascript,
  SiJson,
  SiJupyter,
  SiKotlin,
  SiLua,
  SiMarkdown,
  SiMysql,
  SiPhp,
  SiPython,
  SiR,
  SiRuby,
  SiRust,
  SiScala,
  SiShell,
  SiSqlite,
  SiSwift,
  SiTypescript,
  SiYaml,
} from "react-icons/si";

import { cn } from "@/lib/utils";

type LanguageConfig = {
  icon: IconType;
  bg: string;
  iconClass: string;
};

const LANGUAGE_MAP: Record<string, LanguageConfig> = {
  JavaScript: {
    icon: SiJavascript,
    bg: "bg-[#F7DF1E]",
    iconClass: "text-[#323330]",
  },

  TypeScript: {
    icon: SiTypescript,
    bg: "bg-[#3178C6]",
    iconClass: "text-white",
  },

  Python: {
    icon: SiPython,
    bg: "bg-[#3776AB]",
    iconClass: "text-white",
  },

  Java: {
    icon: Code2,
    bg: "bg-[#ED8B00]",
    iconClass: "text-white",
  },

  "C++": {
    icon: SiCplusplus,
    bg: "bg-[#00599C]",
    iconClass: "text-white",
  },

  C: {
    icon: SiC,
    bg: "bg-[#A8B9CC]",
    iconClass: "text-[#283593]",
  },

  "C#": {
    icon: Code2,
    bg: "bg-[#512BD4]",
    iconClass: "text-white",
  },

  Go: {
    icon: SiGo,
    bg: "bg-[#00ADD8]",
    iconClass: "text-white",
  },

  Rust: {
    icon: SiRust,
    bg: "bg-[#DEA584]",
    iconClass: "text-[#000000]",
  },

  PHP: {
    icon: SiPhp,
    bg: "bg-[#777BB4]",
    iconClass: "text-white",
  },

  Ruby: {
    icon: SiRuby,
    bg: "bg-[#CC342D]",
    iconClass: "text-white",
  },

  Kotlin: {
    icon: SiKotlin,
    bg: "bg-[#7F52FF]",
    iconClass: "text-white",
  },

  Swift: {
    icon: SiSwift,
    bg: "bg-[#F05138]",
    iconClass: "text-white",
  },

  Dart: {
    icon: SiDart,
    bg: "bg-[#0175C2]",
    iconClass: "text-white",
  },

  Scala: {
    icon: SiScala,
    bg: "bg-[#DC322F]",
    iconClass: "text-white",
  },

  "F#": {
    icon: SiFsharp,
    bg: "bg-[#378BBA]",
    iconClass: "text-white",
  },

  R: {
    icon: SiR,
    bg: "bg-[#276DC3]",
    iconClass: "text-white",
  },

  Elixir: {
    icon: SiElixir,
    bg: "bg-[#4B275F]",
    iconClass: "text-white",
  },

  Erlang: {
    icon: SiErlang,
    bg: "bg-[#A90533]",
    iconClass: "text-white",
  },

  Clojure: {
    icon: SiClojure,
    bg: "bg-[#5881D8]",
    iconClass: "text-white",
  },

  Haskell: {
    icon: SiHaskell,
    bg: "bg-[#5D4F85]",
    iconClass: "text-white",
  },

  Crystal: {
    icon: SiCrystal,
    bg: "bg-[#000000]",
    iconClass: "text-white",
  },

  Lua: {
    icon: SiLua,
    bg: "bg-[#000080]",
    iconClass: "text-white",
  },

  HTML: {
    icon: SiHtml5,
    bg: "bg-[#E34F26]",
    iconClass: "text-white",
  },

  CSS: {
    icon: SiCss,
    bg: "bg-[#1572B6]",
    iconClass: "text-white",
  },

  Dockerfile: {
    icon: SiDocker,
    bg: "bg-[#2496ED]",
    iconClass: "text-white",
  },

  Docker: {
    icon: SiDocker,
    bg: "bg-[#2496ED]",
    iconClass: "text-white",
  },

  Shell: {
    icon: SiShell,
    bg: "bg-[#4EAA25]",
    iconClass: "text-white",
  },

  Bash: {
    icon: SiGnubash,
    bg: "bg-[#4EAA25]",
    iconClass: "text-white",
  },

  SQL: {
    icon: SiMysql,
    bg: "bg-[#4479A1]",
    iconClass: "text-white",
  },

  MySQL: {
    icon: SiMysql,
    bg: "bg-[#4479A1]",
    iconClass: "text-white",
  },

  SQLite: {
    icon: SiSqlite,
    bg: "bg-[#003B57]",
    iconClass: "text-white",
  },

  GraphQL: {
    icon: SiGraphql,
    bg: "bg-[#E10098]",
    iconClass: "text-white",
  },

  JSON: {
    icon: SiJson,
    bg: "bg-[#292929]",
    iconClass: "text-white",
  },

  YAML: {
    icon: SiYaml,
    bg: "bg-[#CB171E]",
    iconClass: "text-white",
  },

  Markdown: {
    icon: SiMarkdown,
    bg: "bg-[#083FA1]",
    iconClass: "text-white",
  },

  Jupyter: {
    icon: SiJupyter,
    bg: "bg-[#F37626]",
    iconClass: "text-white",
  },
};

type LanguageIconProps = {
  language?: string | null;
  className?: string;
};

export function LanguageIcon({
  language,
  className,
}: LanguageIconProps) {
  const config = language ? LANGUAGE_MAP[language] : undefined;

  if (!config) {
    return (
      <div
        className={cn(
          "flex size-8 shrink-0 items-center justify-center rounded-md bg-muted",
          className
        )}
      >
        <Code2 className="size-4 text-muted-foreground" />
      </div>
    );
  }

  const Icon = config.icon;

  return (
    <div
      className={cn(
        "flex size-8 shrink-0 items-center justify-center rounded-md",
        config.bg,
        className
      )}
    >
      <Icon className={cn("size-4", config.iconClass)} />
    </div>
  );
}